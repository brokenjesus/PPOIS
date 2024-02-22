package by.lupach.autorent.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {
    private String number;
    private String validThruDate;
    private String holder;
    private short securityCode;
    private boolean valid;
    private final String CARD_NUMBER_PATTERN = "[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$";
    private final String SECURITY_CODE_PATTERN = "^\\d{3}$";
    private final String VALID_THRU_DATE_PATTERN = "^(0[1-9]|1[0-2])\\/\\d{2}$";
    private final String CARDHOLDER_PATTERN = "^[A-Za-z]+(?: [A-Za-z]+)?$";
    private boolean isValidCardNumber(String cardNumberToValidate) {
        Pattern pattern = Pattern.compile(CARD_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(cardNumberToValidate);
        return matcher.matches();
    }

    private boolean isValidCardDate(String cardValidThruDateToValidate) {
        Pattern pattern = Pattern.compile(VALID_THRU_DATE_PATTERN);
        Matcher matcher = pattern.matcher(cardValidThruDateToValidate);
        return matcher.matches();
    }

    private boolean isValidCardholder(String cardHolderToValidate) {
        Pattern pattern = Pattern.compile(CARDHOLDER_PATTERN);
        Matcher matcher = pattern.matcher(cardHolderToValidate);
        return matcher.matches();
    }

    private boolean isValidSecurityCode(String cardSecurityCodeToValidate) {
        Pattern pattern = Pattern.compile(SECURITY_CODE_PATTERN);
        Matcher matcher = pattern.matcher(cardSecurityCodeToValidate);
        return matcher.matches();
    }

    public boolean cardCreditsValidation(String cardNumber, String cardValidThruDate, String cardHolder,
                                         int cardSecurityCode){
        if(!isValidCardNumber(cardNumber)){
            System.out.println("Invalid card number format");
            return false;
        }
        if (!isValidCardDate(cardValidThruDate)){
            System.out.println("Invalid card valid thru date format");
            return false;
        }
        if(!isValidCardholder(cardHolder)){
            System.out.println("Invalid cardholder format");
            return false;
        }
        if(!isValidSecurityCode(String.valueOf(cardSecurityCode))){
            System.out.println("Invalid card security code format");
            return false;
        }
        return true;
    }

    public Card(String cardNumber, String cardValidThruDate, String cardHolder, short cardSecurityCode){
        if (cardCreditsValidation(cardNumber, cardValidThruDate, cardHolder, cardSecurityCode)){
            this.number = cardNumber;
            this.validThruDate = cardValidThruDate;
            this.holder = cardHolder;
            this.securityCode = cardSecurityCode;
            valid = true;
        }else{
            valid = false;
            System.out.println("Try again");
        }
    }

    public boolean makeTransaction(Money money){
        if (true){
            System.out.println("Payment successful");
            return true;
        }else{
            System.out.println("Error: payment error, check if on your card enough money or try again later");
            return false;
        }
    }

    public boolean isValidCredits(){
        return valid;
    }
}
