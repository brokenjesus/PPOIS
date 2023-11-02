package by.lupach.weatherapp.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private final Location location;
    private final List<DailyForecast> dailyForecasts;

    public WeatherForecast(String city) {
        WeatherAPI weatherAPI = new WeatherAPI(city);
        JSONObject jsonData = weatherAPI.getWeather();

        location = initializeLocation(jsonData);
        dailyForecasts = initializeDailyForecast(jsonData);
    }

    public WeatherForecast(String city, JSONObject jsonData) {        //need to run tests
        location = initializeLocation(jsonData);
        dailyForecasts = initializeDailyForecast(jsonData);
    }

    public Location initializeLocation(JSONObject jsonData) {
        JSONArray forecastList = jsonData.getJSONArray("list");
        JSONObject cityData = jsonData.getJSONObject("city");

        String country = cityData.getString("country");
        float coordinatesLon = cityData.getJSONObject("coord").getFloat("lon");
        float coordinatesLat = cityData.getJSONObject("coord").getFloat("lat");
        int timezone = cityData.getInt("timezone");
        int population = cityData.getInt("population");
        String city = cityData.getString("name");
        return new Location(country, city, coordinatesLon, coordinatesLat, population, timezone);
    }



    public List<DailyForecast> initializeDailyForecast(JSONObject jsonData) {
        String currentDate = null;
        Time currentSunrise = null;
        Time currentSunset = null;

        List<DailyForecast> forecast = new ArrayList<>();
        List<HourlyForecast> hourlyForecasts = new ArrayList();

        JSONArray forecastList = jsonData.getJSONArray("list");
        JSONObject cityData = jsonData.getJSONObject("city");

        for (int i = 0; i < forecastList.length(); i++) {
            JSONObject forecastData = forecastList.getJSONObject(i);
            String forecastDate = forecastData.getString("dt_txt").split(" ")[0];

            if (!forecastDate.equals(currentDate)) {
                long sunriseTimestamp = cityData.getLong("sunrise");
                long sunsetTimestamp = cityData.getLong("sunset");
                currentSunrise = new Time(sunriseTimestamp);
                currentSunset = new Time(sunsetTimestamp);
                if (currentDate != null) {
                    forecast.add(new DailyForecast(currentDate, currentSunrise, currentSunset, hourlyForecasts));
                    hourlyForecasts = new ArrayList<>();
                }
                currentDate = forecastDate;
            }

            hourlyForecasts.add(processHourlyForecast(forecastData, hourlyForecasts));
        }

        if (currentDate != null) {
            forecast.add(new DailyForecast(currentDate, currentSunrise, currentSunset, hourlyForecasts));
        }

        return forecast;
    }

    private HourlyForecast processHourlyForecast(JSONObject forecastData, List<HourlyForecast> hourlyForecasts) {
        JSONObject mainData = forecastData.getJSONObject("main");
        Time forecastTime = new Time(forecastData.getLong("dt"));

        float currentTemp = mainData.getFloat("temp");
        float minTemp = mainData.getFloat("temp_min");
        float maxTemp = mainData.getFloat("temp_max");
        float feelsLike = mainData.getFloat("feels_like");
        HourlyTemperature hourlyTemperature = new HourlyTemperature(currentTemp, minTemp, maxTemp, feelsLike);

        int humidity = mainData.getInt("humidity");
        int pressure = mainData.getInt("pressure");

        JSONObject windData = forecastData.getJSONObject("wind");
        int windDeg = windData.getInt("deg");
        float windSpeed = windData.getFloat("speed");
        float windGust = windData.getFloat("gust");
        Wind wind = new Wind(windDeg, windSpeed, windGust);

        JSONObject weatherData = forecastData.getJSONArray("weather").getJSONObject(0);
        String conditions = weatherData.getString("description");
        String iconID = weatherData.getString("icon");

        return new HourlyForecast(forecastTime, hourlyTemperature, humidity, pressure, wind, conditions, iconID);
    }

    public Location getLocation() {
        return location;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }
}
