package com.android.gustavogomesdeoliveira.apigithubchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("location")
    @Expose
    private String location;


    public User(String login, String avatarUrl, String company, String location) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.company = company;
        this.location = location;

    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
