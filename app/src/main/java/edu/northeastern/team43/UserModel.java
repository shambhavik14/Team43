package edu.northeastern.team43;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    public String userId;
    public String userName;
    public ArrayList<SentSticker> sentStickers = new ArrayList<>();
    public ArrayList<ReceivedSticker> receivedStickers = new ArrayList<>();

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public UserModel(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    public UserModel(){}

    public String getUserName() {
        return userName;
    }

    public ArrayList<SentSticker> getSentStickers() {
        return sentStickers;
    }

    public void setSentStickers(ArrayList<SentSticker> sentStickers) {
        this.sentStickers = sentStickers;
    }

    public ArrayList<ReceivedSticker> getReceivedStickers() {
        return receivedStickers;
    }

    public void setReceivedStickers(ArrayList<ReceivedSticker> receivedStickers) {
        this.receivedStickers = receivedStickers;
    }
}
