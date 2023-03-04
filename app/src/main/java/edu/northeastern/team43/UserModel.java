package edu.northeastern.team43;

public class UserModel {

    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public UserModel(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
