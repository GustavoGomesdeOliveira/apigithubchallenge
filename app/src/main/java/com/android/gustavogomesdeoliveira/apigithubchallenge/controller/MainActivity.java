package com.android.gustavogomesdeoliveira.apigithubchallenge.controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.gustavogomesdeoliveira.apigithubchallenge.R;
import com.android.gustavogomesdeoliveira.apigithubchallenge.adapter.UserAdapter;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ClientGithub;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ServiceAPI;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fetchUsers();
    }

    private void initViews(){
        coordinatorLayout = findViewById(R.id.coordinator_main);
        progressBar =  findViewById(R.id.progress_user_list);
        recyclerView =  findViewById(R.id.recycler_view_users);
        recyclerView.smoothScrollToPosition(0);
        swipeRefreshLayout =  findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUsers();
            }
        });
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_search_user);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchUserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    private void fetchUsers(){
        try{
            progressBar.setVisibility(View.VISIBLE);
            ServiceAPI serviceAPI = ClientGithub.getClient().create(ServiceAPI.class);
            Call<List<User>> call = serviceAPI.getUsers();
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                    List<User> users = response.body();
                    if(users != null && users.size() > 0){
                        recyclerView.setAdapter(new UserAdapter(users, getApplicationContext()));
                        recyclerView.smoothScrollToPosition(0);
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, getResources().getString(R.string.fetch_list_user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        fetchUsers();
                                    }
                                }).setActionTextColor(Color.YELLOW);
                        snackbar.show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getResources().getString(R.string.fetch_list_user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchUsers();
                                }
                            }).setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }catch (Exception e){
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.fetch_list_user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchUsers();
                        }
                    }).setActionTextColor(Color.YELLOW);
            snackbar.show();
            progressBar.setVisibility(View.GONE);
        }
    }
}