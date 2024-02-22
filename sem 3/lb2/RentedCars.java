package by.lupach.autorent.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentedCars {
    private ArrayList<Car> rentedCarsList;
    public RentedCars(){
        rentedCarsList = new ArrayList<>();
        DBHandler db = new DBHandler();
        ResultSet result = db.getRentedCarsList();
        try {
            while (result.next()){
                rentedCarsList.add(new Car(result.getInt(DBConst.CARS_ID), result.getString(DBConst.CARS_CAR_NAME),
                        new Money(result.getInt(DBConst.CARS_PRICE)), result.getBoolean(DBConst.CARS_RENTED)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Car> getRentedCarsList() {
        return rentedCarsList;
    }

    public void print(){
        for (int i=0; i<rentedCarsList.size(); i++){
            System.out.println("ID: " + (i+1) +"\t" + rentedCarsList.get(i).getCarName() +"\t\t\t\t" + "price for an hour: " +
                    (rentedCarsList.get(i).getCarPrice().getDollarValueOfCents())+"$");
        }
    }
}
