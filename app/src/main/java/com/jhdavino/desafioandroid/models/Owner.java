package com.jhdavino.desafioandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josehenrique on 30/10/17.
 */

public class Owner {

    @SerializedName("login")
    private String username;
    @SerializedName("avatar_url")
    private String url_img;

    public String getUsername() {
        return username;
    }

    public String getUrl_img() {
        return url_img;
    }

}
