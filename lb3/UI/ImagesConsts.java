package by.lupach.weatherapp.UI;

public class ImagesConsts {
    public static final String sun = "sun.png";
    public static final String fewCloudsWhite = "fewCloudsWhite.png";
    public static final String scatteredCloudsWhite = "scatteredCloudsWhite.png";
    public static final String brokenCloudsWhite = "brokenCloudsWhite.png";
    public static final String showerRainWhite = "showerRainWhite.png";
    public static final String rainWhite = "rainWhite.png";
    public static final String thunderstormWhite = "thunderstormWhite.png";
    public static final String snowWhite = "snowWhite.png";
    public static final String mistWhite = "mistWhite.png";



    public static final String fewCloudsBlack = "fewClouds.png";
    public static final String scatteredCloudsBlack = "scatteredClouds.png";
    public static final String brokenCloudsBlack = "brokenClouds.png";
    public static final String showerRainBlack = "showerRain.png";
    public static final String rainBlack = "rain.png";
    public static final String thunderstormBlack = "thunderstorm.png";
    public static final String snowBlack = "snow.png";
    public static final String mistBlack = "mist.png";

    public static String getImageLinkByID(String ID){
        switch (ID){
            case "01d":return sun;
            case "01n":return sun;
            case "02d":return fewCloudsWhite;
            case "02n":return fewCloudsBlack;
            case "03d":return scatteredCloudsWhite;
            case "03n":return scatteredCloudsBlack;
            case "04d":return brokenCloudsWhite;
            case "04n":return brokenCloudsBlack;
            case "09d":return showerRainWhite;
            case "09n":return showerRainBlack;
            case "10d":return rainWhite;
            case "10n":return rainBlack;
            case "11d":return thunderstormWhite;
            case "11n":return thunderstormBlack;
            case "13d":return snowWhite;
            case "13n":return snowBlack;
            case "50d":return mistWhite;
            case "50n":return mistBlack;
        }
        return "";
    }
}
