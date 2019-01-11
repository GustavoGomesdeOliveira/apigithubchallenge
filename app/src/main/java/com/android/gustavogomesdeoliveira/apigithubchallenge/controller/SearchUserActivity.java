package com.android.gustavogomesdeoliveira.apigithubchallenge.controller;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.gustavogomesdeoliveira.apigithubchallenge.R;
import com.android.gustavogomesdeoliveira.apigithubchallenge.adapter.UserAdapter;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ClientGithub;
import com.android.gustavogomesdeoliveira.apigithubchallenge.api.ServiceAPI;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.SearchUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextSearch;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        initViews();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initViews(){
        coordinatorLayout = findViewById(R.id.coordinator_search_user);
        progressBar =  findViewById(R.id.progress_search_user);
        progressBar.setVisibility(View.GONE);
        recyclerView =  findViewById(R.id.recycler_view_search_users);
        recyclerView.smoothScrollToPosition(0);
        editTextSearch =  findViewById(R.id.user_name_search);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        fetchUsers(editTextSearch.getText().toString());
                    default:
                        return false;
                }
            }
        });
    }

    private void fetchUsers(final String login){
        try{
            progressBar.setVisibility(View.VISIBLE);
            ServiceAPI serviceAPI = ClientGithub.getClient().create(ServiceAPI.class);
            Call<SearchUser> call = serviceAPI.getUsersByLogin(login);
            call.enqueue(new Callback<SearchUser>() {
                @Override
                public void onResponse(@NonNull Call<SearchUser> call, @NonNull Response<SearchUser> response) {
                    SearchUser users = response.body();
                    assert users != null;
                    recyclerView.setAdapter(new UserAdapter(users.getUsers(), getApplicationContext()));
                    recyclerView.smoothScrollToPosition(0);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(@NonNull Call<SearchUser> call, @NonNull Throwable t) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, getResources().getString(R.string.fetch_list_user_failed), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.try_again), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    fetchUsers(login);
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
                            fetchUsers(login);
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
