/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class Account {

    private int id;
    private String accountName;
    private String password;
    private int role;

    private String displayname;
    private String fullname;
    private Timestamp dob;
    private boolean gender;
    private String address;
    private String phone;
    private String email;
    private List<Feedback> feedbackList;
    private List<ResponseContent> responseList;

    public Account() {
    }

    public Account(int id, String accountName, String password, int role, String displayname, String fullname, Timestamp dob, boolean gender, String address, String phone, String email, List<Feedback> feedbackList, List<ResponseContent> responseList) {
        this.id = id;
        this.accountName = accountName;
        this.password = password;
        this.role = role;
        this.displayname = displayname;
        this.fullname = fullname;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.feedbackList = feedbackList;
        this.responseList = responseList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<ResponseContent> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<ResponseContent> responseList) {
        this.responseList = responseList;
    }

}
