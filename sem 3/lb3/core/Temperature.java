package by.lupach.weatherapp.core;

public class Temperature {
    private final int value;        //kelvin value
    Temperature(float value){
        this.value = (int)value;
    }

    public int getKelvinValue() {
        return value;
    }

    public int getCelsiusValue(){
        return toCelsius(value);
    }

    public int getFahrenheitValue(){
        return toFahrenheit(value);
    }

    public int getValue() {
        if (TemperatureUnitsConfig.getUnits() == TemperatureUnits.CELSIUS){
            return getCelsiusValue();
        }
        if (TemperatureUnitsConfig.getUnits() == TemperatureUnits.FAHRENHEIT){
            return getFahrenheitValue();
        }
        return getKelvinValue();
    }

    private int toCelsius(int kelvinValue){
        return kelvinValue - 273;
    }

    private int toFahrenheit(int kelvinValue){
        return toCelsius(kelvinValue)*9/5 + 32;
    }
}
