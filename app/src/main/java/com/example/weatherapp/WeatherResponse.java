package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("weather")
    public List<Weather> weather;

    public class Main {
        @SerializedName("temp")
        public float temp;
    }

    public class Wind {
        @SerializedName("speed")
        public float speed;
    }

    public class Clouds {
        @SerializedName("all")
        public int cloudiness;
    }

    public class Weather {
        @SerializedName("main")
        public String main;
    }
}
