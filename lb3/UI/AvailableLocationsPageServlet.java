package by.lupach.weatherapp.UI;

import by.lupach.weatherapp.UI.pages.HTMLPageContent;
import by.lupach.weatherapp.core.AvailableLocations;
import by.lupach.weatherapp.core.Location;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/locations")
public class AvailableLocationsPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filename = "locations.html";
        response.setContentType("text/html");

        ServletContext context = getServletContext();
        String filePath = context.getRealPath("/WEB-INF/classes/cities_list.xlsx");
        AvailableLocations locations = new AvailableLocations(filePath);

        HTMLPageContent htmlContent = new HTMLPageContent(getServletContext(), filename);
        htmlContent.setHtmlContent(replaceAvailableLocations(htmlContent.getHtmlContent(),locations));
        response.getWriter().write(htmlContent.getHtmlContent());
    }

    private String replaceAvailableLocations(String htmlContent, AvailableLocations locations){
        char currentLetter = ' ';


        StringBuilder availableLocations = new StringBuilder();
        for (Location location: locations.getLocations()){
            String city = location.getCity();

            char firstLetter = city.charAt(0);
            if (firstLetter != currentLetter) {
                if(currentLetter!=' '){
                    availableLocations.append("</div>\n");
                }
                currentLetter = firstLetter;
                availableLocations.append("<div class=\"section availableLocations\">\n" +
                        "                <h1 class=\"availableLocationsFirstLetter\">"+currentLetter+"</h1>\n");
            }
            availableLocations.append("<a href=\"javascript:void(0);\" onclick=\"postCity('"+city+"');\"><h3>"+city+"</h3></a>\n");
        }
        availableLocations.append("</div>\n");



        htmlContent = htmlContent.replace("<!-- availableLocations -->",availableLocations.toString());
        return htmlContent;
    }
}