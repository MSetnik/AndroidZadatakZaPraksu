package com.example.androidpraksazadatak.model;

import java.io.Serializable;

public class article implements Serializable {
    private String Title;
    private String Url;
    private String UrlToImage;


    public article( String title, String url, String urlToImage) {
        Title = title;
        Url = url;
        UrlToImage = urlToImage;
    }


    public String getTitle() {
        return Title;
    }

    public String getUrl() {
        return Url;
    }

    public String getUrlToImage() {
        return UrlToImage;
    }

}
