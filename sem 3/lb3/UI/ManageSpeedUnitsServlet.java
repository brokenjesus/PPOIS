package by.lupach.weatherapp.UI;

import by.lupach.weatherapp.core.SpeedUnits;
import by.lupach.weatherapp.core.SpeedUnitsConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/settings/setSpeedUnits")
public class ManageSpeedUnitsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String unit = request.getParameter("unit");

        if (unit.equals("kmh")) {
            SpeedUnitsConfig.setUnits(SpeedUnits.KILOMETERS_PER_HOUR);
        }else if (unit.equals("mph")){
            SpeedUnitsConfig.setUnits(SpeedUnits.MILES_PER_HOUR);
        }else if (unit.equals("metersPerSecond")){
            SpeedUnitsConfig.setUnits(SpeedUnits.METERS_PER_SECOND);
        }else if (unit.equals("knots")){
            SpeedUnitsConfig.setUnits(SpeedUnits.KNOTS);
        }else {
            SpeedUnitsConfig.setUnits(SpeedUnits.FOOTS_PER_SECOND);
        }
        response.sendRedirect("/settings");
    }
}
