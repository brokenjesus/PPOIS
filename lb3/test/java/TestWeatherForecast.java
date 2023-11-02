import by.lupach.weatherapp.core.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.*;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestWeatherForecast {
    WeatherForecast forecast;
    @Mock
    private HttpURLConnection mockConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        try {
            File fakeDataFile = new File("src/test/resources/minskWeather.json");

            // Create an InputStream from the file
            InputStream fakeInputStream = new FileInputStream(fakeDataFile);

            // Configure the mock HttpURLConnection to return a response code of 200
            Mockito.when(mockConnection.getResponseCode()).thenReturn(200);

            // Configure the mock InputStream to return the fake data
            Mockito.when(mockConnection.getInputStream()).thenReturn(fakeInputStream);

            // Create a WeatherAPI instance with the mock connection
            WeatherAPI weatherAPI = new WeatherAPI("fakeLocation");
            weatherAPI.connection = mockConnection;

            forecast = new WeatherForecast("fakeLocation", weatherAPI.getWeather());
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }
    }

    @Test
    public void testDate() {
        Assert.assertEquals("2023-10-31",forecast.getDailyForecasts().get(0).getDate());
        Assert.assertEquals("2023-11-01",forecast.getDailyForecasts().get(1).getDate());
        Assert.assertEquals("2023-11-02",forecast.getDailyForecasts().get(2).getDate());
        Assert.assertEquals("2023-11-03",forecast.getDailyForecasts().get(3).getDate());
        Assert.assertEquals("2023-11-04",forecast.getDailyForecasts().get(4).getDate());
        Assert.assertEquals("2023-11-05",forecast.getDailyForecasts().get(5).getDate());
    }

    @Test
    public void testLocation(){
        Assert.assertEquals("BY", forecast.getLocation().getCountry());
        Assert.assertEquals("Minsk", forecast.getLocation().getCity());
        Assert.assertEquals(10800, forecast.getLocation().getTimezone().getRawValue());
        Assert.assertEquals(1742124, forecast.getLocation().getPopulation());
        Assert.assertEquals(27.5667, forecast.getLocation().getCoordinatesLon(),0.1);
        Assert.assertEquals(53.9, forecast.getLocation().getCoordinatesLat(),0.1);
    }

    @Test
    public void testDailyMinAndMaxTemperature(){
        Assert.assertEquals(8, forecast.getDailyForecasts().get(0).getTemperature().getMinTemperature().getValue());
        Assert.assertEquals(13, forecast.getDailyForecasts().get(0).getTemperature().getMaxTemperature().getValue());
    }

    @Test
    public void testTodaysForecast(){
        Assert.assertEquals(8, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getTemperature()
                .getCurrentTemperature().getValue());
        Assert.assertEquals(10, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(1).getTemperature()
                .getCurrentTemperature().getValue());
        Assert.assertEquals(11, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(2).getTemperature()
                .getCurrentTemperature().getValue());
        Assert.assertEquals(13, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(3).getTemperature()
                .getCurrentTemperature().getValue());
        Assert.assertEquals(11, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(4).getTemperature()
                .getCurrentTemperature().getValue());
    }

    @Test
    public void testAirConditions(){
        Assert.assertEquals(4, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getTemperature()
                .getCurrentTemperatureFeelsLike().getValue());
        Assert.assertEquals(5.75, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind().getSpeed().
                getValue(),0.1);
        Assert.assertEquals(83, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getHumidity());
        Assert.assertEquals(1006, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getPressure());
    }

    @Test
    public void testWind(){
        Assert.assertEquals(150, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind().getDegree());
        Assert.assertEquals(5.75, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind().getSpeed().getValue(), 0.1);
        Assert.assertEquals(13.19, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind().getGust(),0.1);
    }

    @Test
    public void testSunriseAndSunset(){
        Assert.assertEquals("08:06", forecast.getDailyForecasts().get(0).getSunrise()
                .getTimeValue(forecast.getLocation().getTimezone()));
        Assert.assertEquals("17:39", forecast.getDailyForecasts().get(0).getSunset()
                .getTimeValue(forecast.getLocation().getTimezone()));
    }

    @Test
    public void testDayWorstCondition(){
        Assert.assertEquals("light rain", forecast.getDailyForecasts().get(1)
                .getHourlyForecastsWithTheWorstConditions().getConditions());

        Assert.assertEquals("moderate rain", forecast.getDailyForecasts().get(5)
                .getHourlyForecastsWithTheWorstConditions().getConditions());
    }

    @Test
    public void testHourlyForecastTime(){
        Assert.assertEquals("12:00", forecast.getDailyForecasts().get(0)
                .getHourlyForecasts().get(0).getTime().getTimeValue(forecast.getLocation().getTimezone()));

        Assert.assertEquals(1698753600, forecast.getDailyForecasts().get(0)
                .getHourlyForecasts().get(1).getTime().getRawValue());

    }

    @Test
    public void testSpeed(){
        SpeedUnitsConfig.setUnits(SpeedUnits.KILOMETERS_PER_HOUR);
        Assert.assertEquals(5.75, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind()
                .getSpeed().getValue(), 0.1);
        Assert.assertEquals("km/h", SpeedUnitsConfig.getCurrentUnitsSign());

        SpeedUnitsConfig.setUnits(SpeedUnits.MILES_PER_HOUR);
        Assert.assertEquals(3.57, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind()
                .getSpeed().getValue(), 0.1);
        Assert.assertEquals("mph", SpeedUnitsConfig.getCurrentUnitsSign());


        SpeedUnitsConfig.setUnits(SpeedUnits.METERS_PER_SECOND);
        Assert.assertEquals(1.59, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind()
                .getSpeed().getValue(), 0.1);
        Assert.assertEquals("m/s", SpeedUnitsConfig.getCurrentUnitsSign());


        SpeedUnitsConfig.setUnits(SpeedUnits.KNOTS);
        Assert.assertEquals(3.1, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind()
                .getSpeed().getValue(), 0.1);
        Assert.assertEquals("kt", SpeedUnitsConfig.getCurrentUnitsSign());


        SpeedUnitsConfig.setUnits(SpeedUnits.FOOTS_PER_SECOND);
        Assert.assertEquals(5.24, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getWind()
                .getSpeed().getValue(), 0.1);
        Assert.assertEquals("ft/s", SpeedUnitsConfig.getCurrentUnitsSign());

        SpeedUnitsConfig.setUnits(SpeedUnits.KILOMETERS_PER_HOUR);
    }


    @Test
    public void testTemperature(){
        TemperatureUnitsConfig.setUnits(TemperatureUnits.CELSIUS);
        Assert.assertEquals(8, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getTemperature().
                getCurrentTemperature().getValue());
        Assert.assertEquals("°C",TemperatureUnitsConfig.getCurrentUnitsSign());

        TemperatureUnitsConfig.setUnits(TemperatureUnits.KELVIN);
        Assert.assertEquals(281, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getTemperature().
                getCurrentTemperature().getValue());
        Assert.assertEquals("°K",TemperatureUnitsConfig.getCurrentUnitsSign());

        TemperatureUnitsConfig.setUnits(TemperatureUnits.FAHRENHEIT);
        Assert.assertEquals(46, forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0).getTemperature().
                getCurrentTemperature().getValue());
        Assert.assertEquals("°F",TemperatureUnitsConfig.getCurrentUnitsSign());

        TemperatureUnitsConfig.setUnits(TemperatureUnits.CELSIUS);
    }
}