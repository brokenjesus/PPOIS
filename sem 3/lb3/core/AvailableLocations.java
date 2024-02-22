package by.lupach.weatherapp.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvailableLocations {
    List<Location> locations = new ArrayList<>();
    public AvailableLocations(String filePath) throws IOException {
        ExcelLocationsFileReader excelLocationsFileReader = new ExcelLocationsFileReader(filePath);
        locations = excelLocationsFileReader.readFile();
    }

    public List<Location> getLocations() {
        return locations;
    }
}