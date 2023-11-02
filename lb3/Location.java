package by.lupach.weatherapp.core;

public class Location {
    private final String country;
    private final String city;
    private final double coordinatesLon;
    private final double coordinatesLat;
    private final int population;
    private final Time timezone;

    Location (String country, String city, double coordinatesLon, double coordinatesLat, int population, int timezone){
        this.country = country;
        this.city = city;
        this.coordinatesLon = coordinatesLon;
        this.coordinatesLat = coordinatesLat;
        this.timezone = new Time(timezone);
        this.population = population;
    }

    Location (String country, String city, double coordinatesLon, double coordinatesLat){
        this.country = country;
        this.city = city;
        this.coordinatesLon = coordinatesLon;
        this.coordinatesLat = coordinatesLat;
        this.timezone = null;
        this.population = 0;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getCoordinatesLon() {
        return coordinatesLon;
    }

    public double getCoordinatesLat() {
        return coordinatesLat;
    }

    public Time getTimezone() {
        return timezone;
    }

    public int getPopulation() {
        return population;
    }
}
