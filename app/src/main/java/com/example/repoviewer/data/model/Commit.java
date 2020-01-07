package com.example.repoviewer.data.model;

public class Commit {
    private String url;
    private Author author;
    private Committer commiter;
    private String message;
    private Tree tree;
}

class Author{
    private String name;
    private String date;
}

class Committer{
    private String name;
    private String date;
}

class Tree{
    private String url;
    private String sha;
}

