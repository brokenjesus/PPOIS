package by.lupach.weatherapp.UI.pages;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTMLPageContent {
    private String htmlContent;

    public HTMLPageContent(ServletContext context, String fileName) throws IOException {
        InputStream in = context.getResourceAsStream("/WEB-INF/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();
        htmlContent = content.toString();
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent){
        this.htmlContent = htmlContent;
    }
}