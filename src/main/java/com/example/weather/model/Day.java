package com.example.weather.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Day {

    @SerializedName("mintemp_c")
    private double minTempC;

    @SerializedName("maxtemp_c")
    private double maxTempC;

    @SerializedName("avghumidity")
    private double avgHumidity;

    @SerializedName("maxwind_kph")
    private double maxWindKph;
}
