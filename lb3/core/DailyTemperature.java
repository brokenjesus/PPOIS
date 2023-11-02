package by.lupach.weatherapp.core;

public class DailyTemperature {
    private final Temperature minTemperature;
    private final Temperature maxTemperature;

    DailyTemperature(float minTemperature, float maxTemperature){
        this.minTemperature = new Temperature(minTemperature);
        this.maxTemperature = new Temperature(maxTemperature);
    }

    public Temperature getMinTemperature() {
        return minTemperature;
    }

    public Temperature getMaxTemperature() {
        return maxTemperature;
    }
}
