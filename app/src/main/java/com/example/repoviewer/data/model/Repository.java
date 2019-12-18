package com.example.repoviewer.data.model;

import com.google.gson.annotations.SerializedName;

public class Repository {
    private String name;
    private User user;
    private String description;
    private String url;
    @SerializedName("pushed_at")
    private String pushed;
    @SerializedName("created_at")
    private String created;
    @SerializedName("updated_at")
    private String updated;

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getPushed() {
        return pushed;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }
}
