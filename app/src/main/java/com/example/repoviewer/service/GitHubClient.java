package com.example.repoviewer.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubClient {
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccesToekn> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code,
    )
    @GET("/users/{user}/repos")
    Call<List<>>reposForUser(@Path("user") String user);
}
