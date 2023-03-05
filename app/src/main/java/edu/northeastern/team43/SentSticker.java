package edu.northeastern.team43;

import java.io.Serializable;

public class SentSticker implements Serializable {
    private String stickerId;
    private String sentToUsername;
    private String time;

    public SentSticker(){}
    public SentSticker(String stickerId,String sentToUsername,String time){
        this.stickerId=stickerId;
        this.sentToUsername=sentToUsername;
        this.time=time;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getSentToUsername() {
        return sentToUsername;
    }

    public void setSentToUsername(String sentToUsername) {
        this.sentToUsername = sentToUsername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
