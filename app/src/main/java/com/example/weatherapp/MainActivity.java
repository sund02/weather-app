package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_KEY = "";
    private EditText cityEditText;
    private TextView temperatureText, rainText, windText, cloudinessText;
    private ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Fixed the method call
        setContentView(R.layout.activity_main);

        cityEditText = findViewById(R.id.cityEditText);
        Button searchButton = findViewById(R.id.searchButton);
        temperatureText = findViewById(R.id.temperatureText);
        rainText = findViewById(R.id.rainText);
        windText = findViewById(R.id.windText);
        cloudinessText = findViewById(R.id.cloudinessText);
        backgroundImage = findViewById(R.id.backgroundImage);

        // Set default background image on app startup
        backgroundImage.setImageResource(R.drawable.sunny);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEditText.getText().toString().trim();
                if (!city.isEmpty()) {
                    getWeatherData(city);
                } else {
                    temperatureText.setText("Please enter a city name.");
                }
            }
        });
    }

    private void getWeatherData(String city) {
        WeatherApiService apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getWeather(city, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();

                    // Display Temperature
                    float temp = weatherResponse.main.temp;
                    temperatureText.setText("Temperature: " + temp + " °C");

                    // Display Wind Speed
                    float windSpeed = weatherResponse.wind.speed;
                    windText.setText("Wind Speed: " + windSpeed + " m/s");

                    // Display Cloudiness
                    int cloudiness = weatherResponse.clouds.cloudiness;
                    cloudinessText.setText("Cloudiness: " + cloudiness + " %");

                    // Display Rain Status
                    String rainStatus = "No rain";
                    if (weatherResponse.weather != null && weatherResponse.weather.size() > 0) {
                        String mainWeather = weatherResponse.weather.get(0).main;
                        rainStatus = mainWeather.equalsIgnoreCase("Rain") ? "It’s raining" : rainStatus;
                        setWeatherBackground(mainWeather);
                    }
                    rainText.setText("Rain: " + rainStatus);
                } else {
                    temperatureText.setText("City not found.");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                temperatureText.setText("Failed to get weather data.");
            }
        });
    }

    private void setWeatherBackground(String weather) {
        switch (weather.toLowerCase()) {
            case "clear":
                backgroundImage.setImageResource(R.drawable.sunny);
                break;
            case "rain":
                backgroundImage.setImageResource(R.drawable.rainy);
                break;
            case "clouds":
                backgroundImage.setImageResource(R.drawable.cloudy);
                break;
            default:
                backgroundImage.setImageResource(R.drawable.cloudy); // Fallback to cloudy
                break;
        }
    }
}
