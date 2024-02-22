package by.lupach.weatherapp.UI;

import by.lupach.weatherapp.core.TemperatureUnits;
import by.lupach.weatherapp.core.TemperatureUnitsConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings/setTemperatureUnits")
public class ManageTemperatureUnitsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String unit = request.getParameter("unit");

        if (unit.equals("Celsius")) {
            TemperatureUnitsConfig.setUnits(TemperatureUnits.CELSIUS);
        }else if (unit.equals("Fahrenheit")){
            TemperatureUnitsConfig.setUnits(TemperatureUnits.FAHRENHEIT);
        }else {
            TemperatureUnitsConfig.setUnits(TemperatureUnits.KELVIN);
        }
        response.sendRedirect("/settings");
    }
}

