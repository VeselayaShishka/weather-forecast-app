package com.example.weather.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.weather.api.WeatherApiService;
import com.example.weather.data.exception.ExceptionParser;
import com.example.weather.dto.CityForecastResult;
import com.example.weather.dto.ForecastDay;
import com.example.weather.dto.ForecastResponse;

import retrofit2.Call;
import retrofit2.Response;

public class WeatherService {

    private final WeatherApiService api;
    private final String apiKey;
    private final ExceptionParser parser = new ExceptionParser();

    public WeatherService(WeatherApiService api, String apiKey) {
        this.api = api;
        this.apiKey = apiKey;
    }

    public Map<String, CityForecastResult> fetchNextDayForecast(List<String> cities) {
        Map<String, CityForecastResult> results = new LinkedHashMap<>();

        for (String city : cities) {
            try {
                Call<ForecastResponse> call = api.getForecast(apiKey, city, 2);
                Response<ForecastResponse> response = call.execute();

                if (!response.isSuccessful() || response.body() == null) {
                    results.put(city, CityForecastResult.error(parser.parseApiError(response)));
                    continue;
                }

                List<ForecastDay> days = response.body().forecast().forecastday();
                if (days == null || days.size() < 2) {
                    results.put(city, CityForecastResult.error("No next-day forecast data returned"));
                    continue;
                }

                results.put(city, CityForecastResult.success(days.get(1)));
            } catch (IOException e) {
                results.put(city, CityForecastResult.error("Network error: " + e.getMessage()));
            } catch (Exception e) {
                results.put(city, CityForecastResult.error("Unexpected error: " + e.getMessage()));
            }
        }

        return results;
    }
}
