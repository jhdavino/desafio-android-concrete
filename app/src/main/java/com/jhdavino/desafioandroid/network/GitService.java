package com.jhdavino.desafioandroid.network;

import com.jhdavino.desafioandroid.models.GitResponse;
import com.jhdavino.desafioandroid.models.Pull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by josehenrique on 30/10/17.
 */


public interface GitService {

    @GET("/search/repositories?q=language:Java&sort=stars")
    Single<GitResponse> getRemotesRepository(@Query("page") int page_number);

    @GET("/repos/{user}/{name}/pulls?state=all")
    Single<List<Pull>> getPullRepository(@Path("user") String user, @Path("name") String name);
}
