package com.example.eductionery.Model;

public class cartItems {

    String name;
    String price ;
    String url;
    String state;

    String timeAdded;
    String quant;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    String tag;


    public cartItems(String quant ,String time,String name, String price, String url, String state ,String tag) {
        this.name = name;
        this.timeAdded = time;
        this.price = price;
        this.url = url;
        this.state = state;
        this.quant = quant;
        this.tag = tag;

    }


    public String getQuant() {
        return quant;
    }

    public void setQuant(String quant) {
        this.quant = quant;
    }


    public String getTime() {
        return timeAdded;
    }

    public void setTime(String time) {
        this.timeAdded = time;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }



    public cartItems() {
    }



}
