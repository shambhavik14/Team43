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
}
