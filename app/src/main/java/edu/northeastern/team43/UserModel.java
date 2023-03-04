package edu.northeastern.team43;

import java.io.Serializable;

public class UserModel implements Serializable {

    private String userId;
    private String userName;

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
