package by.lupach.autorent.core;

import java.util.ArrayList;

public class Admin extends User {

    public Admin(User user){
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.location = user.location;
        this.phone = user.phone;
        this.rentedCarID = user.rentedCarID;
        this.rentHistory = user.rentHistory;
        this.balance = user.balance;
    }

    public void changeTheCarPrice(Car car, Money newPrice) {
        db.changeTheCarPriceInDB(car.getCarID(), newPrice.getValue());
    }

    public void addNewCar(Car car){
        db.addTheCarToDB(car);
    }

    public void deleteTheCar(Car car){
        db.deleteTheCar(car);
    }
}
