package com.example.weather.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public class Hour {

    @SerializedName("wind_dir")
    private String windDir;
}
