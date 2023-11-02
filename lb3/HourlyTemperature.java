package by.lupach.weatherapp.core;

public class HourlyTemperature {
    private final Temperature currentTemperature;
    private final Temperature minTemperature;
    private final Temperature maxTemperature;
    private final Temperature currentTemperatureFeelsLike;
    public HourlyTemperature(float currentTemp, float minTemp, float maxTemp, float feelsLike){
        this.currentTemperature= new Temperature(currentTemp);
        this.minTemperature = new Temperature(minTemp);
        this.maxTemperature = new Temperature(maxTemp);
        this.currentTemperatureFeelsLike = new Temperature(feelsLike);
    }

    public Temperature getCurrentTemperature() {
        return currentTemperature;
    }

    public Temperature getMinTemperature() {
        return minTemperature;
    }

    public Temperature getMaxTemperature() {
        return maxTemperature;
    }

    public Temperature getCurrentTemperatureFeelsLike() {
        return currentTemperatureFeelsLike;
    }
}
