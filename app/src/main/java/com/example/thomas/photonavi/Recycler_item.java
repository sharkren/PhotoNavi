package com.example.thomas.photonavi;

/**
 * Created by thomas on 2016-01-19.
 */
public class Recycler_item {

    int imageResId;
    String title;
    String address;
    Float latitude;
    Float longitude;

    int getImageResId(){
        return this.imageResId;
    }
    String getTitle(){
        return this.title;
    }


    public Recycler_item(int image, String title){
        this.imageResId = image;
        this.title = title;
    }

}
