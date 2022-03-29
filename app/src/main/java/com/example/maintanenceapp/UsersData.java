package com.example.maintanenceapp;

public class UsersData {
    private String userid;
    private String fullname;
    private String nric;
    private String phonenumber;
    private String gender;
    private String email;
    private String imageUrl;

    public UsersData(String userid, String fullname, String nric, String phonenumber, String gender, String email, String imageUrl) {
        this.userid = userid;
        this.fullname = fullname;
        this.nric = nric;
        this.phonenumber = phonenumber;
        this.gender = gender;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public UsersData() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageUrl;
    }

    public void setImageURL(String imageURL) {
        this.imageUrl = imageURL;
    }
}
