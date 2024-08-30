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
public class Feedback {
    private int id;
    private FeedbackType feedbackType;
    private String fTitle;
    private String fContent;
    private boolean status;
    private Timestamp createDate;
    private boolean checkPublic;
    private int accountid;
    private String feedbackFile;

    public Feedback() {
    }

    public Feedback(int id, FeedbackType feedbackType, String fTitle, String fContent, boolean status, Timestamp createDate, boolean checkPublic, int accountid, String feedbackFile) {
        this.id = id;
        this.feedbackType = feedbackType;
        this.fTitle = fTitle;
        this.fContent = fContent;
        this.status = status;
        this.createDate = createDate;
        this.checkPublic = checkPublic;
        this.accountid = accountid;
        this.feedbackFile = feedbackFile;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }
    
    public String getfTitle() {
        return fTitle;
    }

    public void setfTitle(String fTitle) {
        this.fTitle = fTitle;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public boolean isCheckPublic() {
        return checkPublic;
    }

    public void setCheckPublic(boolean checkPublic) {
        this.checkPublic = checkPublic;
    }
    
    public int getAccountID() {
        return accountid;
    }

    public void setAccountID(int accountid) {
        this.accountid = accountid;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getFeedbackFile() {
        return feedbackFile;
    }

    public void setFeedbackFile(String feedbackFile) {
        this.feedbackFile = feedbackFile;
    }

    
}
