/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author ADMIN
 */
public class ResponseContent {
    private int id;
    private String rTittle;
    private String rContent;
    private Timestamp responseDate;
    private Timestamp modifiedDate;
    private int accountid;
    private int feedbackid;

    public ResponseContent() {
    }

    public ResponseContent(int id, String rTittle, String rContent, Timestamp responseDate, Timestamp modifiedDate, int accountid, int feedbackid) {
        this.id = id;
        this.rTittle = rTittle;
        this.rContent = rContent;
        this.responseDate = responseDate;
        this.modifiedDate = modifiedDate;
        this.accountid = accountid;
        this.feedbackid = feedbackid;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getrTittle() {
        return rTittle;
    }

    public void setrTittle(String rTittle) {
        this.rTittle = rTittle;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public Timestamp getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public int getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(int feedbackid) {
        this.feedbackid = feedbackid;
    }
}
