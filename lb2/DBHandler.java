package by.lupach.autorent.core;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBHandler extends DBConfigs{
    Connection dbConnection;

    public Connection getUsersDbConnection() throws ClassNotFoundException, SQLException {
        String dbUsersConnectionLink = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbUsersName +
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(dbUsersConnectionLink,dbUser, dbPass);
        return dbConnection;
    }

    public Connection getCarsDbConnection() throws ClassNotFoundException, SQLException {
        String dbUsersConnectionLink = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbCarsName +
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(dbUsersConnectionLink,dbUser, dbPass);
        return dbConnection;
    }

    public boolean checkUserWithThisUsernameAlreadyExistence(String username){
        ResultSet usernameExist = null;
        String findUsernameQuery = "SELECT * FROM " + DBConst.USERS_TABLE + " WHERE " + DBConst.USERS_USERNAME + "=?";
        try {
            PreparedStatement findUsernameRequest = getUsersDbConnection().prepareStatement(findUsernameQuery);
            findUsernameRequest.setString(1, username);
            usernameExist = findUsernameRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return usernameExist.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserWithThisPhoneNumberAlreadyExistence(String phone){
        ResultSet userWithPhoneNumberExist = null;
        String findPhoneNumberQuery = "SELECT * FROM " + DBConst.USERS_TABLE + " WHERE " + DBConst.USERS_PHONE + "=?";
        try {
            PreparedStatement findPhoneNumberRequest = getUsersDbConnection().prepareStatement(findPhoneNumberQuery);
            findPhoneNumberRequest.setString(1, phone);
            userWithPhoneNumberExist = findPhoneNumberRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return userWithPhoneNumberExist.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserWithThisEmailAlreadyExistence(String email){
        ResultSet userWithEmailAlreadyExist = null;

        String findEmailQuery = "SELECT * FROM " + DBConst.USERS_TABLE + " WHERE " + DBConst.USERS_EMAIL + "=?";
        try {
            PreparedStatement findEmailRequest = getUsersDbConnection().prepareStatement(findEmailQuery);
            findEmailRequest.setString(1, email);
            userWithEmailAlreadyExist = findEmailRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return userWithEmailAlreadyExist.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean userAlreadyExist(String username, String phone, String email){
        if (checkUserWithThisUsernameAlreadyExistence(username)){
            System.out.println("User with this username already exist");
            return true;
        }
        if (checkUserWithThisPhoneNumberAlreadyExistence(phone)){
            System.out.println("User with this phone number already exist");
            return true;
        }if(checkUserWithThisEmailAlreadyExistence(email)){
            System.out.println("User with this email already exist");
            return true;
        }

        return false;
    }

    public void updateUserInfo(User user){
        ResultSet result;

        String selectUserQuery = "SELECT * FROM " + DBConst.USERS_TABLE + " WHERE " + DBConst.USERS_USERNAME + "=?";
        try {
            PreparedStatement updateUserInfoRequest = getUsersDbConnection().prepareStatement(selectUserQuery);
            updateUserInfoRequest.setString(1, user.getUsername());
            result = updateUserInfoRequest.executeQuery();
            if (result.next()){
                user.setBalance(result.getInt(DBConst.USERS_BALANCE));
                user.setRentedCarID(result.getInt(DBConst.USERS_RENTED_CAR_ID));
                user.setRentHistory(result.getString(DBConst.USERS_RENT_HISTORY));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void userDepositBalance(User user, int depositedMoney) {
        String updateBalanceQuery = "UPDATE " + DBConst.USERS_TABLE + " SET " + DBConst.USERS_BALANCE + " = " +
                DBConst.USERS_BALANCE + " + ? WHERE " + DBConst.USERS_USERNAME + " = ?";

        try {
            PreparedStatement depositBalanceRequest = getUsersDbConnection().prepareStatement(updateBalanceQuery);
            depositBalanceRequest.setInt(1, depositedMoney);
            depositBalanceRequest.setString(2, user.getUsername());
            depositBalanceRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateUserInfo(user);
    }

    public void userDebitingMoney(User user, int debitedMoney) {
        String updateBalanceQuery = "UPDATE " + DBConst.USERS_TABLE + " SET " + DBConst.USERS_BALANCE + " = " +
                DBConst.USERS_BALANCE + " - ? WHERE " + DBConst.USERS_USERNAME + " = ?";

        try {
            PreparedStatement debitingMoneyRequest = getUsersDbConnection().prepareStatement(updateBalanceQuery);
            debitingMoneyRequest.setInt(1, debitedMoney);
            debitingMoneyRequest.setString(2, user.getUsername());
            debitingMoneyRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateUserInfo(user);
    }


    public void addTheUser(User user){
        String insertIntoDBTableQuery = "INSERT INTO " + DBConst.USERS_TABLE + "(" + DBConst.USERS_FIRSTNAME + "," +
                DBConst.USERS_LASTNAME + "," + DBConst.USERS_EMAIL + "," + DBConst.USERS_USERNAME + "," +
                DBConst.USERS_PASSWORD + "," + DBConst.USERS_LOCATION + "," + DBConst.USERS_PHONE + ","
                + DBConst.USERS_BALANCE + ")" + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            if (userAlreadyExist(user.getUsername(), user.getPhone(), user.getEmail())){
                System.out.println("user already exist");
                return;
            }
            PreparedStatement dbAddUserRequest = getUsersDbConnection().prepareStatement(insertIntoDBTableQuery);
            dbAddUserRequest.setString(1, user.getFirstname());
            dbAddUserRequest.setString(2, user.getLastname());
            dbAddUserRequest.setString(3, user.getEmail());
            dbAddUserRequest.setString(4, user.getUsername());
            dbAddUserRequest.setString(5, user.getPassword());
            dbAddUserRequest.setString(6, user.getLocation());
            dbAddUserRequest.setString(7, user.getPhone());
            dbAddUserRequest.setInt(8, 0);
            dbAddUserRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteTheUser(String username){
        String insertIntoDBTableQuery = "DELETE FROM " + DBConst.USERS_TABLE + " WHERE " +
                DBConst.USERS_USERNAME + " =?";
        try {
            PreparedStatement deleteUserRequest = getUsersDbConnection().prepareStatement(insertIntoDBTableQuery);
            deleteUserRequest.setString(1, username);
            deleteUserRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTheUser(User user){
        ResultSet result = null;

        String selectUserQuery = "SELECT * FROM " + DBConst.USERS_TABLE + " WHERE " + DBConst.USERS_USERNAME + "=? AND " +
                DBConst.USERS_PASSWORD + " COLLATE utf8mb4_bin=?"; //"collate" compare password register
        try {
            PreparedStatement getTheUserRequest = getUsersDbConnection().prepareStatement(selectUserQuery);
            getTheUserRequest.setString(1, user.getUsername());
            getTheUserRequest.setString(2, user.getPassword());
            result = getTheUserRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet getTheAvailableCarsList() {
        ResultSet result = null;

        String selectCarsQuery = "SELECT * FROM " + DBConst.CARS_TABLE+ " WHERE " + DBConst.CARS_RENTED + "=?";

        try {
            PreparedStatement getTheAvailableCarsListRequest = getCarsDbConnection().prepareStatement(selectCarsQuery);
            getTheAvailableCarsListRequest.setBoolean(1, false);
            result = getTheAvailableCarsListRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public  ResultSet getRentedCarsList(){
        ResultSet result = null;

        String selectCarsQuery = "SELECT * FROM " + DBConst.CARS_TABLE+ " WHERE " + DBConst.CARS_RENTED + "=?";

        try {
            PreparedStatement getRentedCarsListRequest = getCarsDbConnection().prepareStatement(selectCarsQuery);
            getRentedCarsListRequest.setBoolean(1, true);
            result = getRentedCarsListRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet getCarByID(int carID){
        ResultSet result = null;
        String selectCarsQuery = "SELECT * FROM " + DBConst.CARS_TABLE+ " WHERE " + DBConst.CARS_ID + "=?";

        try {
            PreparedStatement getCarByIDRequest = getCarsDbConnection().prepareStatement(selectCarsQuery);
            getCarByIDRequest.setInt(1, carID);
            result = getCarByIDRequest.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void changeTheCarPriceInDB(int carID, int carPrice){
        String updateTheCarPriceInDB = "UPDATE " + DBConst.CARS_TABLE + " SET " + DBConst.CARS_PRICE + " = ? WHERE " +
                DBConst.CARS_ID + " = ?";

        try {
            PreparedStatement changeTheCarPriceInDBRequest = getCarsDbConnection().prepareStatement(updateTheCarPriceInDB);
            changeTheCarPriceInDBRequest.setInt(1, carPrice);
            changeTheCarPriceInDBRequest.setInt(2, carID);
            changeTheCarPriceInDBRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addTheCarToDB(Car car){
        String insertIntoDBTableQuery = "INSERT INTO " + DBConst.CARS_TABLE + "(" + DBConst.CARS_CAR_NAME + "," +
                DBConst.CARS_PRICE + ")" + "VALUES(?,?)";
        try {
            PreparedStatement dbAddUserRequest = getCarsDbConnection().prepareStatement(insertIntoDBTableQuery);
            dbAddUserRequest.setString(1, car.getCarName());
            dbAddUserRequest.setInt(2, car.getCarPrice().getValue());
            dbAddUserRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteTheCar(Car car){
        String insertIntoDBTableQuery = "DELETE FROM " + DBConst.CARS_TABLE + " WHERE " +
        DBConst.CARS_ID + " =?";
        try {
            PreparedStatement dbAddUserRequest = getCarsDbConnection().prepareStatement(insertIntoDBTableQuery);
            dbAddUserRequest.setInt(1, car.getCarID());
            dbAddUserRequest.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void rentTheCar(Car car, User user){

        String date = new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(Calendar.getInstance().getTime());

        String updateRentQuery = "UPDATE " + DBConst.CARS_TABLE + " SET " + DBConst.CARS_RENTED + " = ? WHERE " +
                DBConst.CARS_ID + " = ?";
        String updateUserRentedCarIDQuery = "UPDATE " + DBConst.USERS_TABLE + " SET " + DBConst.USERS_RENTED_CAR_ID +
                " = ?, " + DBConst.USERS_RENT_HISTORY + " = CONCAT(" + DBConst.USERS_RENT_HISTORY + ",?) WHERE " +
                DBConst.USERS_USERNAME + " = ?";

        try {
            PreparedStatement updateRentStatement = getCarsDbConnection().prepareStatement(updateRentQuery);
            updateRentStatement.setBoolean(1, true);
            updateRentStatement.setInt(2, car.getCarID());
            updateRentStatement.executeUpdate();

            PreparedStatement updateUserRentedCarIDStatement = getUsersDbConnection().prepareStatement(updateUserRentedCarIDQuery);
            updateUserRentedCarIDStatement.setInt(1, car.getCarID());
            updateUserRentedCarIDStatement.setString(2, car.getCarName()+"\t"+date+"\n");
            updateUserRentedCarIDStatement.setString(3, user.getUsername());
            updateUserRentedCarIDStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        updateUserInfo(user);
    }

    public void removeCurrentlyRentedCarFromUserProfile(User user){
        String removeCurrentlyRentedCarFromUserProfileQuery = "UPDATE " + DBConst.USERS_TABLE + " SET " +
                DBConst.USERS_RENTED_CAR_ID + " = ? WHERE " + DBConst.USERS_USERNAME + " = ?";
        String removeCurrentlyRentedCarFromCarDBQuery = "UPDATE " + DBConst.CARS_TABLE + " SET " + DBConst.CARS_RENTED +
                " = ? WHERE " + DBConst.CARS_ID + " = ?";
        try {
            PreparedStatement removeCurrentlyRentedCarRequest = getUsersDbConnection().prepareStatement(removeCurrentlyRentedCarFromUserProfileQuery);
            removeCurrentlyRentedCarRequest.setInt(1, 0);
            removeCurrentlyRentedCarRequest.setString(2, user.getUsername());
            removeCurrentlyRentedCarRequest.executeUpdate();

            PreparedStatement removeCurrentlyRentedCarFromCarDBRequest = getCarsDbConnection().prepareStatement(removeCurrentlyRentedCarFromCarDBQuery);
            removeCurrentlyRentedCarFromCarDBRequest.setBoolean(1, false);
            removeCurrentlyRentedCarFromCarDBRequest.setInt(2, user.getRentedCarID());
            removeCurrentlyRentedCarFromCarDBRequest.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        updateUserInfo(user);

    }


}
