package by.lupach.weatherapp.core;

public class Time {
    private final long unixTimestampValue;
    Time(long value){
        this.unixTimestampValue = value;
    }

    public String getTimeValue(Time timezone) {
        long hours = ((unixTimestampValue+timezone.getRawValue()) / 3600) % 24;
        long minutes = ((unixTimestampValue+timezone.getRawValue()) % 3600) / 60;

        String formattedTime = String.format("%02d:%02d", hours, minutes);

        return formattedTime;
    }

    public long getRawValue(){
        return unixTimestampValue;
    }
}
