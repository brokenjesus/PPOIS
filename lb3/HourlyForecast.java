package by.lupach.weatherapp.core;

public class HourlyForecast {
    private Time time;
    private HourlyTemperature temperature;
    private int humidity;
    private int pressure;
    private Wind wind;
    private String conditions;
    private String conditionsIconID;

    public HourlyForecast(Time time, HourlyTemperature temperature, int humidity, int pressure, Wind wind,
                          String conditions, String conditionsIconID) {
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.conditions = conditions;
        this.conditionsIconID = conditionsIconID;
    }

    public HourlyTemperature getTemperature() {
        return temperature;
    }

    public Time getTime() {
        return time;
    }
    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public Wind getWind() {
        return wind;
    }

    public String getConditions() {
        return conditions;
    }

    public String getConditionsIconID() {
        return conditionsIconID;
    }
}
