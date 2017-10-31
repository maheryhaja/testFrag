package fr.hdb.artibip.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidatorManager {

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static boolean isPasswordSecured(String password){
        String passwordRegEx;
        Pattern pattern;
        // Minimum 6 characters au moins 1 Alphabet et 1 chiffre
        passwordRegEx = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        // Compare the regex with the password
        pattern = Pattern.compile(passwordRegEx);
        Matcher matcher = pattern.matcher(password);
        return true;
    }

    public static boolean isPasswordValid(String password){
        if(password.length()>=8)
            return true;

        return false;
    }

    public static boolean isPasswordMatch(String first, String second){
        if(first.equals(second))
            return true;
        return false;
    }

    public static boolean isTelephonValid(String tel){
        if(tel.length() >= 7)
            return true;
        return false;
    }

    public static boolean isNomEtabliValide(String nomEtabli){
        return nomEtabli.length()>=5;
    }
}
