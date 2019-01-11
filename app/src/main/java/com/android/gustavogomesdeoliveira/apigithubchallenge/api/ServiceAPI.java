package com.android.gustavogomesdeoliveira.apigithubchallenge.api;

import com.android.gustavogomesdeoliveira.apigithubchallenge.model.Repository;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.SearchUser;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.User;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceAPI {

    @GET("search/users")
    Call<SearchUser> getUsersByLogin(@Query("q") String login);

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String login);

    @GET("users/{user}/repos")
    Call<List<Repository>> getRepositories(@Path("user") String login);
}
