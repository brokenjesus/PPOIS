package by.lupach.autorent.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car {
    private int carID;
    private String carName;

    private Money price;
    private boolean rentStatus;

    Car(int carID, String carName, Money price, boolean isRented){
        this.carID = carID;
        this.carName = carName;
        this.price = price;
        this.rentStatus = isRented;
    }
    public Car(String carName, Money price){
        this.carName = carName;
        this.price = price;
    }

    public Car(int carID){
        getCarByID(carID);
    }

    public int getCarID(){
        return this.carID;
    }

    public String getCarName(){
        return this.carName;
    }

    public Money getCarPrice(){
        return this.price;
    }

    public boolean getRentStatus(){
        return rentStatus;
    }
    public void getCarByID(int carID){
        DBHandler db = new DBHandler();
        ResultSet result = db.getCarByID(carID);
        try {
            if (result.next()){
                this.carID = result.getInt(DBConst.CARS_ID);
                this.carName = result.getString(DBConst.CARS_CAR_NAME);
                this.price = new Money(result.getInt(DBConst.CARS_PRICE));
                this.rentStatus = result.getBoolean(DBConst.CARS_RENTED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void print(){
        System.out.println(carName +"\t\t\t\t" + "price for an hour: " +
                    (price.getDollarValueOfCents())+"$" + "rent status: "+ rentStatus);
    }

}
