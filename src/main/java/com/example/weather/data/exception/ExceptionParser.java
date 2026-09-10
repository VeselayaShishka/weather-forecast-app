package com.example.weather.data.exception;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

public class ExceptionParser {

    private final Gson gson = new Gson();

    public String parseApiError(Response<?> response) {
        ResponseBody errorBody = response.errorBody();

        if (errorBody == null) {
            return "HTTP " + response.code() + " - no error body";
        }

        String rawBody;

        try {
            rawBody = errorBody.string();
        } catch (IOException e) {
            return "HTTP " + response.code() + " - could not read error body";
        }

        try {
            CityForecastException apiError =
                    gson.fromJson(rawBody, CityForecastException.class);

            if (apiError != null && apiError.error() != null) {
                return "HTTP " + response.code() + " [" + apiError.error().code() + "] "
                        + apiError.error().message();
            }

        } catch (JsonSyntaxException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }

        return "HTTP " + response.code() + " - " + rawBody;
    }
}