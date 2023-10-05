package by.lupach.autorent.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String username;
    protected String password;
    protected String location;
    protected String phone;
    protected int rentedCarID = 0;
    protected String rentHistory;

    protected Money balance;

    public User(){}

    public User(String firstname, String lastname, String email, String username, String password, String location,
                String phone, int rentedCarID, String rentHistory, int balance){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.location = location;
        this.phone = phone;
        this.rentedCarID = rentedCarID;
        this.rentHistory = rentHistory;
        this.balance = new Money(balance);
    }

    public void setRentedCarID(int rentedCarID){
        this.rentedCarID = rentedCarID;
    }
    public void setRentHistory(String rentHistory){
        this.rentHistory = rentHistory;
    }
    public void setBalance(int balance){
        this.balance = new Money(balance);
    }


    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getLocation(){
        return this.location;
    }

    public String getPhone(){
        return this.phone;
    }

    public int getRentedCarID(){
        return this.rentedCarID;
    }

    public String getRentHistory() {
        return rentHistory;
    }

    public Money getBalance(){
        return balance;
    }


    public boolean isLoggedIn(){
        return firstname != null;
    }

    public void depositBalanceViaCard(Money depositMoney, Card card){
        if (!card.isValidCredits()){
            System.out.println("Invalid card credits");
        }else {
            if (card.makeTransaction(depositMoney)) {
                DBHandler db = new DBHandler();
                db.userDepositBalance(this, depositMoney.getValue());
            }
        }
    }

    public void rentTheCar(Car car){
        if (car.getCarPrice().getValue() <= balance.getValue()) {
            DBHandler db = new DBHandler();
            db.rentTheCar(car, this);
            db.userDebitingMoney(this, car.getCarPrice().getValue());
            System.out.println("You have successfully rented " + car.getCarName());
        } else {
            System.out.println("Seems like you don't have enough money on your account to rent this car");
        }
    }

    public void passTheCar(){
        if (rentedCarID!=0){
            DBHandler db = new DBHandler();
            db.removeCurrentlyRentedCarFromUserProfile(this);
            System.out.println("Car passed");
        }else{
            System.out.println("You have no rented car");
        }
    }

    public void print(){
        System.out.println("firstname: " + firstname + "\nlastname: " + lastname + "\nemail: " + email + "\nusername: "
                + username + "\npassword: " + password + "\nlocation: " + location + "\nphone: " + phone + "\nbalance: "
                + balance.getDollarValueOfCents() + "$\nCurrently rented car: " +
                new Car(rentedCarID).getCarName() + "\nYour rent history:\n" + rentHistory);

    }

}
