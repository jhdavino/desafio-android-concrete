package com.jhdavino.desafioandroid.models;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by josehenrique on 30/10/17.
 */

public class Pull {
    private String html_url;
    private String state;
    private String title;
    private String body;
    private Date created_at;
    private User user;

    public String getHtml_url() {
        return html_url;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public User getUser() {
        return user;
    }

    public String getCreated_at() {

        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(created_at);
    }
}
