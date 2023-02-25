package com.example.eductionery.Model;

public class items {
    private String id;
    private String imgurl;
    private String itemname;
    private String itemtag;
    private String itemcategories;
    private String itemdesc;



    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private  String state;

    String uploadedate;
    String uploadedtime;


    public items(){}

    public items(String id ,String imgurl, String itemname, String itemtag, String itemcategories, String itemdesc,String uploadedate,String uploadedtime,String state) {
        this.imgurl = imgurl;
        this.itemname = itemname;
        this.itemtag = itemtag;
        this.itemcategories = itemcategories;
        this.itemdesc = itemdesc;
        this.id= id;
        this.uploadedate=uploadedate;
        this.uploadedtime=uploadedtime;
        this.state=state;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemtag() {
        return itemtag;
    }

    public void setItemtag(String itemtag) {
        this.itemtag = itemtag;
    }

    public String getItemcategories() {
        return itemcategories;
    }

    public void setItemcategories(String itemcategories) {
        this.itemcategories = itemcategories;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getUploadedate() {
        return uploadedate;
    }

    public void setUploadedate(String uploadedate) {
        this.uploadedate = uploadedate;
    }

    public String getUploadedtime() {
        return uploadedtime;
    }

    public void setUploadedtime(String uploadedtime) {
        this.uploadedtime = uploadedtime;
    }





}
