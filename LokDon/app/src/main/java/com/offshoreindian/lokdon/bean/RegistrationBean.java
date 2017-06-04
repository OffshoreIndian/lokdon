package com.offshoreindian.lokdon.bean;

/**
 * Created by praveshkumar on 30/12/16.
 */

public class RegistrationBean {

    private String username;
    private String password;
    private String emailID;
    private String contactNo;
    private String firstName;
    private String lastNamer;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNamer() {
        return lastNamer;
    }

    public void setLastNamer(String lastNamer) {
        this.lastNamer = lastNamer;
    }
}
