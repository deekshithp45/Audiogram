package com.agnt45.audiogram.Classes;

/**
 * Created by ar265 on 9/17/2017.
 */

public class Users {



    private String name;
    private String status;
    private String image;
    private String thumbnail_image;

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public Users(){

    }

    public Users(String name, String status, String image,String thumbnail_image) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.thumbnail_image = thumbnail_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
