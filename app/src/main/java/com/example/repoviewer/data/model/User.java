package com.example.repoviewer.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String location;
    private String email;
    private String bio;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }


}
