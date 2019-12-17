package com.example.repoviewer.service;

import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.data.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubClient {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );

    @GET("/user")
    Call<User> getUser(@HeaderMap Map<String, String> headers);

    @GET("/users/{user}/repos")
    Call<List<Repository>>reposForUser(
            @Path("user") String user,
            @Query("sort") String sort,
            @Query("direction") String direction
    );
}
