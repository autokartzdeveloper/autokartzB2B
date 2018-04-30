package com.autokartz.autokartz.utils.util;

import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Cortana on 1/8/2018.
 */

public class InputValidation {

    public static boolean validateFirstName(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your first name");
            return false;
        }
    }

    public static boolean validateLastName(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your last name");
            return false;
        }
    }

    public static boolean validateAddress(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your address");
            return false;
        }
    }

    public static boolean validateCity(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your city");
            return false;
        }
    }

    public static boolean validateState(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your state");
            return false;
        }
    }

    public static boolean validatePin(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your pincode");
            return false;
        }
    }

    public static boolean validateEmail(EditText emailEt) {
        String email = emailEt.getText().toString().trim();
        String regex = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n" +
                "      @[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
        if (email == null || email.isEmpty()) {
            emailEt.setError("Please enter your email");
        } else if (!email.matches(Patterns.EMAIL_ADDRESS.pattern())) {
            emailEt.setError("Please enter valid email address");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validatePanNumber(EditText panEt) {
        String pan = panEt.getText().toString().trim();
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(pan);
        if (pan == null || pan.isEmpty()) {
            panEt.setError("Please enter your PAN");
        }
        // Check if pattern matches
        else if (!matcher.matches()) {
            panEt.setError("Enter Your Correct PAN");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validateIfscCode(EditText nameEt) {

        String IFSC = nameEt.getText().toString().trim();
        //  String regress = "[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$/";
        Pattern pattern = Pattern.compile("[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}");
        Matcher matcher = pattern.matcher(IFSC);
        if (IFSC == null || IFSC.isEmpty()) {
            nameEt.setError("Please enter your IFSC code");
        } else if (!matcher.matches()) {
            nameEt.setError("Please enter your correct IFSC code");
        } else {
            return true;
        }

        return false;
    }

    public static boolean validateGstNumber(EditText nameEt) {
        String gst = nameEt.getText().toString().trim();
        //  String regress = "[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$/";
        Pattern pattern = Pattern.compile("[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}");
        Matcher matcher = pattern.matcher(gst);
        if (gst == null || gst.isEmpty()) {
            nameEt.setError("Please enter your Gst Number");
        } else if (!matcher.matches()) {
            nameEt.setError("Please enter your correct Gst Number");
        } else {
            return true;
        }

        return false;
    }

    public static boolean validateMobile(EditText mobileEt) {
        String mobile = mobileEt.getText().toString().trim();
        String regex = "[0-9]+";
        if (mobile == null || mobile.isEmpty()) {
            mobileEt.setError("Please enter your phone number");
        } else if (!mobile.matches(regex)) {
            mobileEt.setError("Please enter valid phone number");
        } else if (!(mobile.length() == 10)) {
            mobileEt.setError("Phone number digits should be 10 ");
        } else {
            return true;
        }
        return false;
    }

    public static boolean validatePassword(EditText passEt) {
        String pass = passEt.getText().toString().trim();
        if (pass == null || pass.isEmpty()) {
            passEt.setError("Please enter password");
        } else if (!(pass.length() >= 6 && pass.length() <= 12)) {
            passEt.setError("Password length should be 6 to 12");
        } else {
            return true;
        }
        return false;
    }


    public static boolean validateBankAcName(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your A/c No..");
            return false;
        }
    }


    public static boolean validateBranch(EditText nameEt) {
        String name = nameEt.getText().toString().trim();
        if (name != null && !name.isEmpty()) {
            return true;
        } else {
            nameEt.setError("Please enter your Branch");
            return false;
        }
    }


}