package com.example.weather.dto;

import com.example.weather.model.Day;
import com.example.weather.model.Hour;

import java.util.List;

public record ForecastDay(Day day, List<Hour> hour) {

    public String getWindDirection() {
        if (hour == null || hour.size() <= 12) {
            return "N/A";
        }
        return hour.get(12).getWindDir();
    }
}
