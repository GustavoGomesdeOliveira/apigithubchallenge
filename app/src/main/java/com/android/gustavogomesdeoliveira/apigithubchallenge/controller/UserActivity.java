package com.android.gustavogomesdeoliveira.apigithubchallenge.controller;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.gustavogomesdeoliveira.apigithubchallenge.R;
import com.android.gustavogomesdeoliveira.apigithubchallenge.adapter.RepositoryAdapter;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ClientGithub;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ServiceAPI;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.Repository;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.User;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private ImageView userAvatar;
    private CoordinatorLayout coordinatorLayout;
    private TextView userName, userCompany, userLocation;
    private RecyclerView recyclerView;
    private User user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initViews();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        String avatarURL = Objects.requireNonNull(getIntent().getExtras()).getString("avatar");
        Glide.with(this).load(avatarURL).into(userAvatar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.user_details));
        fetchUser(getIntent().getExtras().getString("login"));
        fetchRepository(getIntent().getExtras().getString("login"));
    }

    private void initViews(){
        coordinatorLayout = findViewById(R.id.coordinator_user);
        progressBar =  findViewById(R.id.progress_user_repository);
        userAvatar =  findViewById(R.id.user_avatar_detail);
        userName =  findViewById(R.id.user_name_detail);
        userCompany =  findViewById(R.id.user_company);
        userLocation =  findViewById(R.id.user_location);
        recyclerView =  findViewById(R.id.recycler_view_repository);
    }

    private void fetchUser(final String userLogin){
        try{
            ServiceAPI serviceAPI = ClientGithub.getClient().create(ServiceAPI.class);
            Call<User> call = serviceAPI.getUser(userLogin);
            call.enqueue((new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    user = response.body();
                    assert user != null;
                    if(user.getLogin() != null){
                        userName.setText(user.getLogin());
                    }
                    if(user.getCompany() != null){
                        userCompany.setText(user.getCompany());
                    }
                    if(user.getLocation() != null){
                        userLocation.setText(user.getLocation());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getResources().getString(R.string.fetch__user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchUser(userLogin);
                                }
                            }).setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }));
        }catch (Exception e){
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.fetch__user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchUser(userLogin);
                        }
                    }).setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private void fetchRepository(final String userName){
        try{
            progressBar.setVisibility(View.VISIBLE);
            ServiceAPI serviceAPI = ClientGithub.getClient().create(ServiceAPI.class);
            Call<List<Repository>> call = serviceAPI.getRepositories(userName);
            call.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(@NonNull Call<List<Repository>> call, @NonNull Response<List<Repository>> response) {
                    List<Repository> repositories = response.body();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(new RepositoryAdapter(repositories));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(@NonNull Call<List<Repository>> call, @NonNull Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getResources().getString(R.string.fetch_list_repositories_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchRepository(userName);
                                }
                            }).setActionTextColor(Color.YELLOW);
                    snackbar.show();
                    progressBar.setVisibility(View.GONE);
                }
            });

        }catch (Exception e){
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getResources().getString(R.string.fetch_list_repositories_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchRepository(userName);
                        }
                    }).setActionTextColor(Color.YELLOW);
            snackbar.show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
