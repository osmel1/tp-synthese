package com.example.tp_synthese.models;

public class User {
    private String userId ;
    private String userName;
    private String imgUrl ;

    public User() {
    }

    public User(String userId, String userName, String imgUrl) {
        this.userId = userId;
        this.userName = userName;
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
