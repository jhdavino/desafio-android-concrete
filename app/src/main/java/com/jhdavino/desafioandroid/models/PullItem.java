package com.jhdavino.desafioandroid.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josehenrique on 30/10/17.
 */


public class PullItem implements Parcelable {

    @SuppressWarnings("unused")
    private static final Parcelable.Creator<PullItem> CREATOR = new Parcelable.Creator<PullItem>() {
        @Override
        public PullItem createFromParcel(Parcel in) {
            return new PullItem(in);
        }

        @Override
        public PullItem[] newArray(int size) {
            return new PullItem[size];
        }
    };
    private String html_url;
    private String state;
    private String title;
    private String body;
    private String created_at;
    private String login;
    private String avatar_url;

    //constructor
    public PullItem() {
    }


    protected PullItem(Parcel in) {
        this.setHtml_url(in.readString());
        this.setState(in.readString());
        this.setTitle(in.readString());
        this.setBody(in.readString());
        this.setCreated_at(in.readString());
        this.setLogin(in.readString());
        this.setAvatar_url(in.readString());
    }

    public static Creator<PullItem> getCREATOR() {
        return CREATOR;
    }

    public static PullItem create(Pull item) {
        PullItem pull = new PullItem();
        pull.setAvatar_url(item.getUser().getAvatar_url());
        pull.setBody(item.getBody());
        pull.setCreated_at(item.getCreated_at());
        pull.setHtml_url(item.getHtml_url());
        pull.setLogin(item.getUser().getLogin());
        pull.setTitle(item.getTitle());
        pull.setState(item.getState());

        return pull;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getHtml_url());
        dest.writeString(getState());
        dest.writeString(getTitle());
        dest.writeString(getBody());
        dest.writeString(getCreated_at());
        dest.writeString(getLogin());
        dest.writeString(getAvatar_url());
    }


}
