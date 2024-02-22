package by.lupach.weatherapp.UI;

import by.lupach.weatherapp.UI.pages.HTMLPageContent;
import by.lupach.weatherapp.core.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@WebServlet("/weather")
public class WeatherPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCityValue = request.getParameter("searchCity");

        doGet(request, response, searchCityValue);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response, String city)
            throws ServletException, IOException {
        String filename = "weatherInCity.html";
        response.setContentType("text/html");

        WeatherForecast forecast = new WeatherForecast(city);

        Location location = forecast.getLocation();

        DailyForecast todayForecast = forecast.getDailyForecasts().get(0);
        HourlyForecast currentWeather = forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0);


        HTMLPageContent htmlContent = new HTMLPageContent(getServletContext(), filename);

        htmlContent.setHtmlContent(replaceWeatherInfo(htmlContent.getHtmlContent(), forecast));
        htmlContent.setHtmlContent(replaceAirConditions(htmlContent.getHtmlContent(),currentWeather));
        htmlContent.setHtmlContent(replaceSunriseAndSunsetTime(htmlContent.getHtmlContent(), todayForecast,location));
        htmlContent.setHtmlContent(replaceTodaysForecastItems(htmlContent.getHtmlContent(), todayForecast,location));
        htmlContent.setHtmlContent(replaceWeekForecastItems(htmlContent.getHtmlContent(), forecast));

        response.getWriter().write(htmlContent.getHtmlContent());
    }

    private String getHtmlContent(String fileName) throws IOException {
        InputStream in = getServletContext().getResourceAsStream("/WEB-INF/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();
        return content.toString();
    }

    private String replaceWeatherInfo(String htmlContent, WeatherForecast forecast){
        DailyForecast todayForecast = forecast.getDailyForecasts().get(0);
        HourlyForecast currentWeather = forecast.getDailyForecasts().get(0).getHourlyForecasts().get(0);
        htmlContent = htmlContent.replace("<!-- location -->", forecast.getLocation().getCity());
        htmlContent = htmlContent.replace("<!-- maxTemp -->", todayForecast.getTemperature()
                .getMaxTemperature().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign());
        htmlContent = htmlContent.replace("<!-- minTemp -->", todayForecast.getTemperature()
                .getMinTemperature().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign());
        htmlContent = htmlContent.replace("<!-- current temperature -->",  currentWeather.getTemperature()
                .getCurrentTemperature().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign());
        htmlContent = htmlContent.replace("<img name=\"currentConditionIcon\" src=\"images/sun.png\"", "<img name=\"currentConditionIcon\" src=\"images/" + ImagesConsts.getImageLinkByID(currentWeather.getConditionsIconID()) + "\"");
        return htmlContent;
    }
    private String replaceTodaysForecastItems(String htmlContent, DailyForecast todayForecast, Location location){
        StringBuilder todaysForecastItems = new StringBuilder();
        Time timezone = location.getTimezone();
        for (HourlyForecast forecast : todayForecast.getHourlyForecasts()){
            todaysForecastItems.append("<div class=\"todayForecastItem\">\n" +
                    "                    <h4 class=\"sectionTitleText\">"+forecast.getTime().getTimeValue(timezone)+"</h4>\n" +
                    "                    <img src=\"images/"+ImagesConsts.getImageLinkByID(forecast.getConditionsIconID())+"\" width=\"70px\">\n" +
                    "                    <h3>"+forecast.getTemperature().getCurrentTemperature().getValue()+
                    TemperatureUnitsConfig.getCurrentUnitsSign()+"</h3>\n" +
                    "                </div>");
        }
        htmlContent = htmlContent.replace("<!-- todayForecastItem -->", todaysForecastItems.toString());
        return htmlContent;
    }

    private String replaceAirConditions(String htmlContent, HourlyForecast currentWeather){
        htmlContent = htmlContent.replace("<!-- FeelLikeValue -->",   currentWeather.getTemperature()
                .getCurrentTemperatureFeelsLike().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign());
        htmlContent = htmlContent.replace("<!-- WindSpeed -->", currentWeather.getWind().getSpeed().getValue()+
                SpeedUnitsConfig.getCurrentUnitsSign());
        htmlContent = htmlContent.replace("<!-- Humidity -->", currentWeather.getHumidity()+"%");
        return htmlContent;
    }

    private String replaceSunriseAndSunsetTime(String htmlContent, DailyForecast forecast, Location location){
        Time timezone = location.getTimezone();
        htmlContent = htmlContent.replace("<!-- SunriseTime -->",   forecast.getSunrise().getTimeValue(timezone));
        htmlContent = htmlContent.replace("<!-- SunsetTime -->", forecast.getSunset().getTimeValue(timezone));
        return htmlContent;
    }

    private String replaceWeekForecastItems(String htmlContent, WeatherForecast forecast){
        StringBuilder todaysForecastItems = new StringBuilder();
        for (DailyForecast dailyForecast : forecast.getDailyForecasts()){
            todaysForecastItems.append("<div class=\"weekForecastItem\">\n" +
                                            " <h3>"+dailyForecast.getDate()+"</h3>\n" +
                                            "<div class=\"weekForecastItemCondition\">\n" +
                                                "<img src=\"images/"+ImagesConsts.getImageLinkByID(dailyForecast.getHourlyForecastsWithTheWorstConditions().getConditionsIconID())+"\" width=\"50px\">\n" +
                                                "<h4>"+dailyForecast.getHourlyForecastsWithTheWorstConditions().getConditions()+"</h4>\n" +
                                            "</div>\n" +
                                            "<div class=\"weekForecastTemperature\">\n" +
                                                "<h4 class=\"weekForecastMaxTemperature\">"+dailyForecast.getTemperature().getMaxTemperature().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign() +"/</h4><h4 class=\"weekForecastMinTemperature\">"+dailyForecast.getTemperature().getMinTemperature().getValue()+ TemperatureUnitsConfig.getCurrentUnitsSign()+"</h4>\n" +
                                            "</div>\n" +
                                        "</div>");
        }
        htmlContent = htmlContent.replace("<!-- weekForecastItem -->", todaysForecastItems.toString());
        return htmlContent;
    }
}

