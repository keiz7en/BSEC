package com.bsec.oop;

public class User {
    private int id;
    private String userId;
    private String fullName;
    private String password;
    private String userType;
    private String createdAt;

    public User(int id, String userId, String fullName, String password, String userType, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.fullName = fullName;
        this.password = password;
        this.userType = userType;
        this.createdAt = createdAt;
    }


    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userType='" + userType + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}