package com.example.repoviewer.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.Repository;

import java.util.ArrayList;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder> {

    private ArrayList<Repository> mRepositories;

    public void setRepositories(ArrayList<Repository> repositories){
        mRepositories = repositories;
        notifyDataSetChanged();
    }

    public RepoListAdapter(){
        mRepositories = new ArrayList<>();
    }

    @NonNull
    @Override
    public RepoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_list, parent, false);
        return new RepoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListViewHolder holder, int position) {
        String title = mRepositories.get(position).getName();
        String description = mRepositories.get(position).getDescription();
        holder.repoName.setText(title);
        if(!isNullOrEmpty(description)){
            holder.repoDescription.setText(description);
        } else{
            holder.repoDescription.setText(R.string.description_not_available);
        }
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    class RepoListViewHolder extends RecyclerView.ViewHolder{
        TextView repoName;
        TextView repoDescription;

        public RepoListViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = (TextView) itemView.findViewById(R.id.tv_repo_name);
            repoDescription = (TextView) itemView.findViewById(R.id.tv_repo_description);
        }
    }
}
