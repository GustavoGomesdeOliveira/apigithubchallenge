package com.android.gustavogomesdeoliveira.apigithubchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repository {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("watchers_count")
    @Expose
    private String watchersCount;

    @SerializedName("stargazers_count")
    @Expose
    private String stargazersCount;

    @SerializedName("forks_count")
    @Expose
    private String forksCount;

    public Repository(String name, String watchersCount, String stargazersCount, String forksCount) {
        this.name = name;
        this.watchersCount = watchersCount;
        this.stargazersCount = stargazersCount;
        this.forksCount = forksCount;
    }

    public String getName() {
        return name;
    }

    public String getWatchersCount() {
        return watchersCount;
    }

    public String getStargazersCount() {
        return stargazersCount;
    }

    public String getForksCount() {
        return forksCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWatchersCount(String watchersCount) {
        this.watchersCount = watchersCount;
    }

    public void setStargazersCount(String stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public void setForksCount(String forksCount) {
        this.forksCount = forksCount;
    }
}
