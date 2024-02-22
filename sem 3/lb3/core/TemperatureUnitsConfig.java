package by.lupach.weatherapp.core;

public class TemperatureUnitsConfig {
    private static TemperatureUnits units = TemperatureUnits.CELSIUS;

    public static TemperatureUnits getUnits() {
        return units;
    }

    public static void setUnits(TemperatureUnits units) {
        TemperatureUnitsConfig.units = units;
    }

    public static String getCurrentUnitsSign(){
        if (units == TemperatureUnits.CELSIUS){
            return "°C";
        }
        if (units == TemperatureUnits.FAHRENHEIT){
            return "°F";
        }
        return "°K";
    }
}
