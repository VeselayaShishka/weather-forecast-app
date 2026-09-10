package com.example.weather.service;

import java.util.Map;

import com.example.weather.dto.CityForecastResult;
import com.example.weather.model.Day;
import com.example.weather.dto.ForecastDay;


public class PrinterService {

    private static final String ROW_FORMAT =
            "%-12s | %8s | %8s | %11s | %10s | %-9s%n";

    public void print(Map<String, CityForecastResult> results) {
        System.out.printf(ROW_FORMAT, "City", "Min t", "Max t", "Humidity %", "Wind km/h", "Wind Direction");
        printSeparator();

        for (Map.Entry<String, CityForecastResult> entry : results.entrySet()) {
            String city = entry.getKey();
            CityForecastResult result = entry.getValue();

            if (!result.isSuccess()) {
                System.out.printf("%-12s | %s%n", city, "ERROR - " + result.errorMessage());
                continue;
            }

            ForecastDay forecastDay = result.forecastDay();
            Day day = forecastDay.day();

            System.out.printf(
                    ROW_FORMAT,
                    city,
                    String.format("%.1f C", day.getMinTempC()),
                    String.format("%.1f C", day.getMaxTempC()),
                    String.format("%.0f", day.getAvgHumidity()),
                    String.format("%.1f", day.getMaxWindKph()),
                    forecastDay.getWindDirection()
            );
        }
    }

    private void printSeparator() {
        System.out.println("-".repeat(78));
    }
}
