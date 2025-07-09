package com.example.weatherapp;

public class WeatherData {
    private String time;
    private double temperature;
    private long relativeHumidity;
    private double windSpeed;

    public WeatherData(String time, double temperature, long relativeHumidity, double windSpeed) {
        this.time = time;
        this.temperature = temperature;
        this.relativeHumidity = relativeHumidity;
        this.windSpeed = windSpeed;
    }
    //getters
    public String getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }

    public long getRelativeHumidity() {
        return relativeHumidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
