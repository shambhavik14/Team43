package edu.northeastern.team43;

import java.io.Serializable;
import java.util.ArrayList;

public class UserModel implements Serializable {

    private String userId;
    private String userName;
    private ArrayList<SentSticker> sentStickers;
    private ArrayList<ReceivedSticker> receivedStickers;

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
}
