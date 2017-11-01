package com.jhdavino.desafioandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josehenrique on 30/10/17.
 */

public class Item {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("full_name")
    private String full_name;
    @SerializedName("forks")
    private int num_fork;
    @SerializedName("stargazers_count")
    private int num_start;
    @SerializedName("owner")
    private Owner owner;

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public String getFull_name() {
        return full_name;
    }


    public int getNum_fork() {
        return num_fork;
    }


    public int getNum_start() {
        return num_start;
    }


    public Owner getOwner() {
        return owner;
    }

}
