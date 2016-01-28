package com.example.thomas.photonavi;

/**
 * Created by thomas on 2016-01-19.
 */
public class Recycler_item {

    String imageUrl;
    String title;
    String address;
    Double latitude;
    Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = Double.valueOf(latitude);
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = Double.valueOf(longitude);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recycler_item(String imageUrl, String title, String address, String latitude,  String longitude) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.address = address;
        this.latitude = Double.valueOf(latitude);
        this.longitude = Double.valueOf(longitude);

    }

    public Recycler_item(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

}
