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
}
