package com.example.weather;

import java.util.List;
import java.util.Map;

import com.example.weather.api.RetrofitClient;
import com.example.weather.api.WeatherApiService;
import com.example.weather.dto.CityForecastResult;
import com.example.weather.service.PrinterService;
import com.example.weather.service.WeatherService;


public class Main {

    private static final List<String> CITIES = List.of("Ch1sinau", "Madrid", "Kyiv", "Amsterdam");

     static void main(String[] args) {
        String apiKey = System.getenv("WEATHER_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Error: WEATHER_API_KEY environment variable is not set.\n" +
                    "Set it with your WeatherAPI.com API key before running the app");
            return;
        }

        WeatherApiService api = RetrofitClient.getService();
        WeatherService weatherService = new WeatherService(api, apiKey);

        Map<String, CityForecastResult> results = weatherService.fetchNextDayForecast(CITIES);

        new PrinterService().print(results);
        RetrofitClient.shutdown();
    }
}
