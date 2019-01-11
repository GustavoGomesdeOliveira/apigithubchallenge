package com.android.gustavogomesdeoliveira.apigithubchallenge.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.gustavogomesdeoliveira.apigithubchallenge.R;
import com.android.gustavogomesdeoliveira.apigithubchallenge.model.Repository;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {

    private List<Repository> repositoryList;

    public RepositoryAdapter(List<Repository> repositoryList) {
        this.repositoryList = repositoryList;
    }

    @NonNull
    @Override
    public RepositoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_repository, viewGroup, false);
        return new RepositoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryAdapter.ViewHolder viewHolder, int i) {
        viewHolder.repositoryName.setText(repositoryList.get(i).getName());
        viewHolder.repositoryWatchers.setText(repositoryList.get(i).getWatchersCount());
        viewHolder.repositoryStars.setText(repositoryList.get(i).getStargazersCount());
        viewHolder.repositoryForks.setText(repositoryList.get(i).getForksCount());
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView repositoryName, repositoryWatchers, repositoryStars, repositoryForks;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            repositoryName = itemView.findViewById(R.id.name_repository);
            repositoryWatchers = itemView.findViewById(R.id.repository_watchers);
            repositoryStars = itemView.findViewById(R.id.repository_stars);
            repositoryForks = itemView.findViewById(R.id.repository_forks);
        }
    }
}
