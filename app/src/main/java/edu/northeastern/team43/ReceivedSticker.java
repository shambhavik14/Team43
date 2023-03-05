package edu.northeastern.team43;

import java.io.Serializable;

public class ReceivedSticker implements Serializable {
    private String stickerId;
    private String receivedFromUsername;
    private String time;

    public ReceivedSticker(){}
    public ReceivedSticker(String stickerId,String receivedFromUsername,String time){
        this.stickerId=stickerId;
        this.receivedFromUsername=receivedFromUsername;
        this.time=time;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public String getReceivedFromUsername() {
        return receivedFromUsername;
    }

    public void setReceivedFromUsername(String receivedFromUsername) {
        this.receivedFromUsername = receivedFromUsername;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
