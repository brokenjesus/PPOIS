import by.lupach.weatherapp.core.WeatherAPI;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;

public class TestWeatherAPI {

    @Mock
    private HttpURLConnection mockConnection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWeatherAPI() {
        try {
            File fakeDataFile = new File("src/test/resources/minskWeather.json");

            InputStream fakeInputStream = new FileInputStream(fakeDataFile);

            Mockito.when(mockConnection.getResponseCode()).thenReturn(200);

            Mockito.when(mockConnection.getInputStream()).thenReturn(fakeInputStream);

            WeatherAPI weatherAPI = new WeatherAPI("fakeLocation");

            Field connectionField = WeatherAPI.class.getDeclaredField("connection");
            connectionField.setAccessible(true);
            connectionField.set(weatherAPI, mockConnection);

            JSONObject jsonData = weatherAPI.getWeather();

            JSONObject cityData = jsonData.getJSONObject("city");
            String country = cityData.getString("country");
            String cityName = cityData.getString("name");


            Assert.assertEquals("BY", country);
            Assert.assertEquals("Minsk", cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

