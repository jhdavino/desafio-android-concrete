package com.jhdavino.desafioandroid.models;

/**
 * Created by josehenrique on 30/10/17.
 */

public class User {

    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
