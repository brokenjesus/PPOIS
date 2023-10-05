package by.lupach.autorent.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvailableCars{
    private ArrayList<Car> availableCarList;

    public AvailableCars(){
        availableCarList = new ArrayList<>();
        DBHandler db = new DBHandler();
        ResultSet result = db.getTheAvailableCarsList();
        try {
            while (result.next()){
                availableCarList.add(new Car(result.getInt(DBConst.CARS_ID), result.getString(DBConst.CARS_CAR_NAME),
                        new Money(result.getInt(DBConst.CARS_PRICE)), result.getBoolean(DBConst.CARS_RENTED)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Car> getAvailableCarList() {
        return availableCarList;
    }

    public void print(){
        for (int i=0; i<availableCarList.size(); i++){
            System.out.println("ID: " + (i+1) +"\t" + availableCarList.get(i).getCarName() +"\t\t\t\t" + "price for an hour: " +
                    (availableCarList.get(i).getCarPrice().getDollarValueOfCents())+"$");
        }
    }
}
