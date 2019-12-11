package com.example.repoviewer.data.model;

public class Repository {
    private String name;
    private User user;
    private String htmlUrl;
    private String description;
    private String url;

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
