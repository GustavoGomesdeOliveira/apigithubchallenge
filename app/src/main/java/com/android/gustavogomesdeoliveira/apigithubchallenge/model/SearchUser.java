package com.android.gustavogomesdeoliveira.apigithubchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUser {

    @SerializedName("items")
    @Expose
    private List<User> users;

    public SearchUser(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
