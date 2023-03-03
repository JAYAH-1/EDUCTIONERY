package com.example.eductionery.Model;

import android.net.Uri;

import java.io.Serializable;

public class Users implements Serializable {


    String status;
    String userID;
    String fullname;
    String address;
    String email;
    String password;
    String confirmedPass;
    String postal;
    String street;
    String userType;
    String region ;
    String itemSate;
    String accountStatus;
    String phone;
    String imgurl;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
    public String getImgurl() {
        return imgurl;
    }

    public Users(String status ,String imgurl ,String userID,String userType,String phone, String fullname, String address, String email, String password, String confirmedPass, String postal, String street, String region ,String accountStatus ,String itemSate) {

        this.status=status;
        this.imgurl =imgurl;
        this.userID=userID;
        this.itemSate = itemSate;
        this.accountStatus = accountStatus;
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmedPass = confirmedPass;
        this.postal = postal;
        this.street = street;
        this.region = region;
        this.userType=userType;

    }


    public String getItemSate() {
        return itemSate;
    }

    public void setItemSate(String itemSate) {
        this.itemSate = itemSate;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Users() {

    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPass() {
        return confirmedPass;
    }

    public void setConfirmedPass(String confirmedPass) {
        this.confirmedPass = confirmedPass;
    }


}
