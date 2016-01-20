package com.example.thomas.photonavi;

/**
 * Created by thomas on 2016-01-19.
 */
public class Recycler_item {

    int image;
    String title;
    String address;
    Float latitude;
    Float longitude;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }


    public Recycler_item(int image, String title){
        this.image = image;
        this.title = title;
    }

}
