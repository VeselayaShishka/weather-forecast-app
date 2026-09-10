package com.example.weather.dto;

public record CityForecastResult(ForecastDay forecastDay, String errorMessage) {

    public static CityForecastResult success(ForecastDay day) {
        return new CityForecastResult(day, null);
    }

    public static CityForecastResult error(String message) {
        return new CityForecastResult(null, message);
    }

    public boolean isSuccess() {
        return forecastDay != null;
    }
}