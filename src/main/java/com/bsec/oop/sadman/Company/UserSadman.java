package com.bsec.oop.sadman.Company;

import java.io.Serializable;

public class UserSadman implements Serializable {
    private final String name;
    private int id;
    private String password;
    private int totalShare, availableShare;

    public UserSadman(String name, int id, String password, int totalShare, int availableShare) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.totalShare = totalShare;
        this.availableShare = availableShare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getTotalShare() {
        return totalShare;
    }

    public void setTotalShare(int totalShare) {
        this.totalShare = totalShare;
    }

    public int getAvailableShare() {
        return availableShare;
    }

    public void setAvailableShare(int availableShare) {
        this.availableShare = availableShare;
    }

    @Override
    public String toString() {
        return "UserSadman{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", totalShare=" + totalShare +
                ", availableShare=" + availableShare +
                '}';
    }
}