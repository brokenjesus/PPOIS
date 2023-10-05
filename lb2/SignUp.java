package by.lupach.autorent.core;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp {
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

    public SignUp(User user){
        db = new DBHandler();
        boolean validUser = signUpValidation(user);
        if (validUser){
            db.addTheUser(user);
        }else{
            System.out.println("Invalid user values, try again");
            user.logOut();
        }
    }
}
