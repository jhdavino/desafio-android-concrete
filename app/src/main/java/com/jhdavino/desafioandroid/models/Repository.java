package com.jhdavino.desafioandroid.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josehenrique on 30/10/17.
 */

public class Repository implements Parcelable {

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };
    private String name;
    private String description;
    private String full_name;
    private String username;
    private String url_img;
    private int num_fork;
    private int num_start;

    //constructor
    public Repository() {
    }

    protected Repository(Parcel in) {
        name = in.readString();
        description = in.readString();
        full_name = in.readString();
        username = in.readString();
        url_img = in.readString();
        num_fork = in.readInt();
        num_start = in.readInt();
    }

    public static Repository create(Item item) {
        Repository rep = new Repository();
        rep.setUsername(item.getOwner().getUsername());
        rep.setUrl_img(item.getOwner().getUrl_img());
        rep.setNum_start(item.getNum_start());
        rep.setNum_fork(item.getNum_fork());
        rep.setFull_name(item.getFull_name());
        rep.setDescription(item.getDescription());
        rep.setName(item.getName());

        return rep;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getNum_fork() {
        return num_fork;
    }

    public void setNum_fork(int num_fork) {
        this.num_fork = num_fork;
    }

    public int getNum_start() {
        return num_start;
    }

    public void setNum_start(int num_start) {
        this.num_start = num_start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(full_name);
        parcel.writeString(username);
        parcel.writeString(url_img);
        parcel.writeInt(num_fork);
        parcel.writeInt(num_start);
    }
}
