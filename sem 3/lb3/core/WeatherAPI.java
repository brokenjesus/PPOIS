package by.lupach.weatherapp.core;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {
    private final String apiKey = "test?@/test";
    private URL apiUrl;
    public HttpURLConnection connection;
    public WeatherAPI(String location) {
        try {
            String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=" + apiKey;
            apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject getWeather(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder apiResponse = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null){
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            apiResponse.append(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new JSONObject(apiResponse.toString());

    }
}
