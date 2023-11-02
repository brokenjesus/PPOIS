package by.lupach.weatherapp.core;

public class SpeedUnitsConfig {
    private static SpeedUnits units = SpeedUnits.KILOMETERS_PER_HOUR;

    public static SpeedUnits getUnits() {
        return units;
    }

    public static void setUnits(SpeedUnits units) {
        SpeedUnitsConfig.units = units;
    }

    public static String getCurrentUnitsSign(){
        if (units == SpeedUnits.KILOMETERS_PER_HOUR){
            return "km/h";
        }
        if (units == SpeedUnits.METERS_PER_SECOND){
            return "m/s";
        }

        if (units == SpeedUnits.MILES_PER_HOUR){
            return "mph";
        }

        if (units == SpeedUnits.KNOTS){
            return "kt";
        }
        return "ft/s";
    }
}