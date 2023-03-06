package edu.northeastern.team43;

import java.io.Serializable;
import java.util.Objects;

public class ReceivedSticker implements Serializable {
    private String stickerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReceivedSticker)) return false;
        ReceivedSticker that = (ReceivedSticker) o;
        return getStickerId().equals(that.getStickerId()) && getReceivedFromUsername().equals(that.getReceivedFromUsername()) && getTime().equals(that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStickerId(), getReceivedFromUsername(), getTime());
    }

    @Override
    public String toString() {
        return "ReceivedSticker{" +
                "stickerId='" + stickerId + '\'' +
                ", receivedFromUsername='" + receivedFromUsername + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

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
