package com.jhdavino.desafioandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by josehenrique on 30/10/17.
 */

public class GitResponse {
    @SerializedName("total_count")
    private Integer totalCount;
    @SerializedName("incomplete_results")
    private Boolean incompleteResults;
    @SerializedName("items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}
