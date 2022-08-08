package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);
    //check the email, throw an illegalArgumentException
    public Customer (String firstName, String lastName, String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid email");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String toString() {
        return "First Name: " + firstName
                + " Last Name: " + lastName + " Email: "
                + email;
    }
}
