package com.example.esercitazionebonusdl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CancellationException;

public class User implements Serializable {

    private String username, password, city, imgPath, isAdmin, birthDate;

    protected static ArrayList<User> users = new ArrayList<>();

    public User(){

        this.setUsername("");
        this.setPassword("");
        this.setBirthDate("");
        this.setImgPath("");
        this.setCity("");
        this.setIsAdmin("false");
    }

    public User(String username, String password, String city, String imgPath,
                String birthDate, String isAdmin) {

        this.setUsername(username);
        this.setPassword(password);
        this.setBirthDate(birthDate);
        this.setImgPath(imgPath);
        this.setCity(city);
        this.setIsAdmin(isAdmin);

        boolean check = false;

        for(int i = 0; i < users.size(); i++){

            if(users.get(i).getUsername().equals(username)) {
                check = true;
                break;
            }
        }

        if(!check)
            users.add(this);

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

}
