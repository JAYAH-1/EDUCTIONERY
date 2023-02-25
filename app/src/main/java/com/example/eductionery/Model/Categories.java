package com.example.eductionery.Model;

public class Categories {

    private int imageCategory;
    private String categoryName;


    public Categories(){}

    public Categories(int imageCategory, String categoryName) {
        this.imageCategory = imageCategory;
        this.categoryName = categoryName;
    }

    public int getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(int imageCategory) {
        this.imageCategory = imageCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
