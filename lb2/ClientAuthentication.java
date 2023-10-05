package by.lupach.autorent.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientAuthentication {
    private final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PHONE_PATTERN = "^\\+\\d{1,3} \\(\\d{1,3}\\) \\d{3}-\\d{4}$";
    private final String LOCATION_PATTERN = "^[A-Za-z]+, [A-Za-z]+$";
    User user;
    DBHandler db;

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isValidLocation(String location) {
        Pattern pattern = Pattern.compile(LOCATION_PATTERN);
        Matcher matcher = pattern.matcher(location);
        return matcher.matches();
    }

    private boolean signUpValidation(User user){
        if (user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getEmail().isEmpty() ||
                user.getUsername().isEmpty()|| user.getPassword().isEmpty() || user.getLocation().isEmpty() ||
                user.getPhone().isEmpty()) {
            System.out.println("Empty fields, try again");
            return false;
        }else{
            boolean result = true;
            if(!isValidEmail(user.getEmail())){
                System.out.println("Invalid email format");
                result = false;
            }
            if (!isValidPhoneNumber(user.getPhone())){
                System.out.println("Invalid phone number format");
                result = false;
            }
            if(!isValidLocation(user.getLocation())){
                System.out.println("Invalid location format");
                result = false;
            }
            return result;
        }
    }

    public ClientAuthentication(){
        db = new DBHandler();
    }

    public User logOut(User user){
        user = new User(null, null, null, null, null, null,
                null, 0, null, 0);
        return user;
    }

    public void signUp(User user){
        boolean validUser = signUpValidation(user);
        if (validUser){
            db.addTheUser(user);
        }else{
            System.out.println("Invalid user values, try again");
            logOut(user);
        }
    }

    public User logInViaUsername(User user, String username, String password){
        ResultSet result = db.getTheUser(username, password);

        try {
            if(result.next()){
                user = logInTheUser(result);
                System.out.println("Welcome " + user.username);
            }else{
                System.out.println("User not found, try again");
                user.username = user.password = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private User logInTheUser(ResultSet result){
        try {
            user = new User(result.getString(DBConst.USERS_FIRSTNAME), result.getString(DBConst.USERS_LASTNAME),
                    result.getString(DBConst.USERS_EMAIL), result.getString(DBConst.USERS_USERNAME),
                    result.getString(DBConst.USERS_PASSWORD), result.getString(DBConst.USERS_LOCATION),
                    result.getString(DBConst.USERS_PHONE), result.getInt(DBConst.USERS_RENTED_CAR_ID),
                    result.getString(DBConst.USERS_RENT_HISTORY), result.getInt(DBConst.USERS_BALANCE));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
