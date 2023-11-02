package by.lupach.weatherapp.core;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelLocationsFileReader {
    FileInputStream file;
    Workbook workbook;
    public ExcelLocationsFileReader(String filePath) throws IOException {
        file = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(file);
    }
    public List<Location> readFile() throws IOException {
        String city = null;
        String country = null;
        double lon=0;
        double lat=0;
        List<Location> locations = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        try {
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < row.getPhysicalNumberOfCells() ;
                j++){
                    switch (j){
                        case 0:
                            city=row.getCell(j).getStringCellValue();
                            break;
                        case 1:
                            lon= row.getCell(j).getNumericCellValue();
                            break;
                        case 2:
                            lat= row.getCell(j).getNumericCellValue();
                            break;
                        case 3:
                            country=row.getCell(j).getStringCellValue();
                            break;
                    }
                }
                locations.add(new Location(country, city, lon,lat));
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locations;
    }
}
