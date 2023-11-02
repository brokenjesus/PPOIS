import by.lupach.weatherapp.core.AvailableLocations;
import by.lupach.weatherapp.core.ExcelLocationsFileReader;
import by.lupach.weatherapp.core.Location;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class TestAvailableLocations {
    @Test
    public void testAvailableLocations() throws IOException {
        String filePath = "src/main/resources/cities_list.xlsx";
        AvailableLocations availableLocations = new AvailableLocations(filePath);
        List<Location> locations = availableLocations.getLocations();


        Assert.assertEquals("Abidjan",locations.get(0).getCity());
        Assert.assertEquals("Ivory Coast",locations.get(0).getCountry());
        Assert.assertEquals(-4.00167,locations.get(0).getCoordinatesLon(),0.1);
        Assert.assertEquals(5.35444,locations.get(0).getCoordinatesLat(),0.1);
    }
}
