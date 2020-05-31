package com.example.foodbooking;

public class ItemList {
    private String foodName, foodLink, foodLocation, foodImage;

    public ItemList() {
    }

    public ItemList(String foodLocation,String foodName, String foodImage,String foodLink) {
        this.foodName = foodName;
        this.foodLink = foodLink;
        this.foodImage = foodImage;
        this.foodLocation = foodLocation;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodLink() {
        return foodLink;
    }

    public String getFoodLocation() {
        return foodLocation;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodLink(String foodLink) {
        this.foodLink = foodLink;
    }

    public void setFoodLocation(String foodLocation) {
        this.foodLocation = foodLocation;
    }

}
