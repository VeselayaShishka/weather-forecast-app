package com.example.weather.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.weatherapi.com/";
    private static WeatherApiService service;
    private static OkHttpClient httpClient;

    public static WeatherApiService getService() {
        if (service == null) {

            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(WeatherApiService.class);
        }
        return service;
    }
    public static void shutdown() {
        if (httpClient == null) {
            return;
        }
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
        if (httpClient.cache() != null) {
            try {
                httpClient.cache().close();
            } catch (java.io.IOException e) {
                System.out.println("Error closing cache" + e.getMessage());
            }
        }
    }
}
