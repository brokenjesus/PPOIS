package by.lupach.weatherapp.core;

public class Speed {
    float value;        //km/h value

    Speed(float value){
        this.value = value;
    }


    public float getValue() {
        if (SpeedUnitsConfig.getUnits() == SpeedUnits.KILOMETERS_PER_HOUR){
            return Math.round(getKMHValue() * 100) / 100.0f;
        }
        if (SpeedUnitsConfig.getUnits() == SpeedUnits.METERS_PER_SECOND){
            return Math.round(getMetersPerSecondValue() * 100) / 100.0f;
        }
        if (SpeedUnitsConfig.getUnits() == SpeedUnits.MILES_PER_HOUR){
            return Math.round(getMPHValue() * 100) / 100.0f;
        }
        if (SpeedUnitsConfig.getUnits() == SpeedUnits.KNOTS){
            return Math.round(getKnotsValue() * 100) / 100.0f;
        }
        return Math.round(getFootPerSecondValue() * 100) / 100.0f;
    }

    public float getKMHValue() {
        return value;
    }

    public float getMetersPerSecondValue(){
        return (float) (value/3.6);
    }

    public float getMPHValue(){
        return (float) (value/1.609);
    }

    public float getKnotsValue(){
        return (float) (value/1.852);
    }

    public float getFootPerSecondValue(){
        return (float) (value/1.097);
    }
}
