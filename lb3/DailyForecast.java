package by.lupach.weatherapp.core;

import java.util.List;

public class DailyForecast {
    private final String date;
    private final Time sunrise;
    private final Time sunset;
    private final List<HourlyForecast> hourlyForecasts;
    private final DailyTemperature temperature;

    public DailyForecast(String date, Time sunrise, Time sunset, List<HourlyForecast> hourlyForecasts) {
        this.date = date;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.hourlyForecasts = hourlyForecasts;
        temperature = findMinAndMaxDayTemperature();
    }

    private DailyTemperature findMinAndMaxDayTemperature(){
        float dayMaxTemperature = hourlyForecasts.get(0).getTemperature().getMaxTemperature().getKelvinValue();
        float dayMinTemperature = hourlyForecasts.get(0).getTemperature().getMinTemperature().getKelvinValue();
        for (HourlyForecast forecast : hourlyForecasts){
            if (forecast.getTemperature().getMinTemperature().getKelvinValue()<dayMinTemperature){
                dayMinTemperature = forecast.getTemperature().getMinTemperature().getKelvinValue();
            }

            if (forecast.getTemperature().getMaxTemperature().getKelvinValue()>dayMaxTemperature){
                dayMaxTemperature = forecast.getTemperature().getMinTemperature().getKelvinValue();
            }
        }

        return new DailyTemperature(dayMinTemperature, dayMaxTemperature);
    }

    public HourlyForecast getHourlyForecastsWithTheWorstConditions(){
        HourlyForecast hourlyForecastWiTheWorstCondition = hourlyForecasts.get(0);

        for (HourlyForecast forecast: hourlyForecasts){
            if (Integer.parseInt(forecast.getConditionsIconID().replaceAll("[^0-9]", ""))>
                    Integer.parseInt(hourlyForecastWiTheWorstCondition.getConditionsIconID().replaceAll("[^0-9]", ""))){
                hourlyForecastWiTheWorstCondition = forecast;
            }
        }
        return hourlyForecastWiTheWorstCondition;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public String getDate() {
        return date;
    }

    public DailyTemperature getTemperature() {
        return temperature;
    }

    public Time getSunrise() {
        return sunrise;
    }

    public Time getSunset() {
        return sunset;
    }

}