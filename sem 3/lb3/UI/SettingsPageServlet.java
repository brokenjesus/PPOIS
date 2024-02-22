package by.lupach.weatherapp.UI;

import by.lupach.weatherapp.UI.pages.HTMLPageContent;
import by.lupach.weatherapp.core.SpeedUnits;
import by.lupach.weatherapp.core.SpeedUnitsConfig;
import by.lupach.weatherapp.core.TemperatureUnits;
import by.lupach.weatherapp.core.TemperatureUnitsConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings")
public class SettingsPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = "settings.html";
        response.setContentType("text/html");

        HTMLPageContent htmlContent = new HTMLPageContent(getServletContext(), filename);

        htmlContent.setHtmlContent(replaceTemperatureUnitsCheckBoxStatus(htmlContent.getHtmlContent()));
        htmlContent.setHtmlContent(replaceSpeedUnitsCheckBoxStatus(htmlContent.getHtmlContent()));

        response.getWriter().write(htmlContent.getHtmlContent());
    }

    private String replaceTemperatureUnitsCheckBoxStatus(String htmlContent){
        if (TemperatureUnitsConfig.getUnits() == TemperatureUnits.CELSIUS){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"Celsius\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"Celsius\">");
        }else if (TemperatureUnitsConfig.getUnits() == TemperatureUnits.FAHRENHEIT){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"Fahrenheit\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"Fahrenheit\">");
        }else{
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"Kelvin\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"Kelvin\">");
        }

        return htmlContent;
    }

    private String replaceSpeedUnitsCheckBoxStatus(String htmlContent){
        if (SpeedUnitsConfig.getUnits() == SpeedUnits.KILOMETERS_PER_HOUR){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"kmh\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"kmh\">");
        }else if (SpeedUnitsConfig.getUnits() == SpeedUnits.MILES_PER_HOUR){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"mph\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"mph\">");
        }else if (SpeedUnitsConfig.getUnits() == SpeedUnits.METERS_PER_SECOND){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"metersPerSecond\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"metersPerSecond\">");
        }else if (SpeedUnitsConfig.getUnits() == SpeedUnits.KNOTS){
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"knots\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"knots\">");
        }else{
            htmlContent = htmlContent.replace("<input type=\"radio\" name=\"unit\" value=\"footsPerSecond\">",
                    "<input type=\"radio\" checked=\"checked\" name=\"unit\" value=\"footsPerSecond\">");
        }

        return htmlContent;
    }
}