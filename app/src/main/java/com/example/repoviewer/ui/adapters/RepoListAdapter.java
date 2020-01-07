package com.example.repoviewer.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.Repository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder> {

    private ArrayList<Repository> mRepositories;

    private final RepoClickHandler mRepoClickHandler;

    public interface RepoClickHandler{
        void onRepoClick(Repository repository);
    }

    public void setRepositories(ArrayList<Repository> repositories){
        mRepositories = repositories;
        notifyDataSetChanged();
    }

    public RepoListAdapter(RepoClickHandler repoClickHandler){
        mRepositories = new ArrayList<>();
        mRepoClickHandler = repoClickHandler;
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
        holder.repoUpdated.setText(convertTime(mRepositories.get(position).getUpdated()));
        if(!isNullOrEmpty(description)){
            holder.repoDescription.setText(description);
        } else{
            holder.repoDescription.setText(R.string.description_not_available);
        }
    }
    private String convertTime(String updatedDate) {
        String dateTime;
        String[] splitString = updatedDate.split("T");

        String date = splitString[0];
        String inputDatePattern = "YYYY-MM-DD";
        String targetDatePattern = "MM-DD-YYYY";

        String time = splitString[1];
        String inputTimePattern = "HH:MM:SSZ";
        String targetTimePattern = "HH:MM";
        dateTime = simpleDateFormatter(
                date,
                targetDatePattern,
                inputDatePattern
        ) + " " + simpleDateFormatter(
                time,
                targetTimePattern,
                inputTimePattern
        );
        return dateTime;
    }

    private String simpleDateFormatter(String input, String targetPattern, String inputPattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        Date date;
        try{
            date = simpleDateFormat.parse(input);
        }catch (ParseException e) {
            e.printStackTrace();
            return input;
        }
        simpleDateFormat = new SimpleDateFormat(targetPattern, Locale.ENGLISH);
        assert date != null;
        return simpleDateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    private boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    class RepoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView repoName;
        TextView repoDescription;
        TextView repoUpdated;

        public RepoListViewHolder(@NonNull View itemView) {
            super(itemView);
            repoName = (TextView) itemView.findViewById(R.id.tv_repo_name);
            repoDescription = (TextView) itemView.findViewById(R.id.tv_repo_description);
            repoUpdated = (TextView) itemView.findViewById(R.id.tv_repo_updated);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mRepoClickHandler.onRepoClick(mRepositories.get(adapterPosition));
        }
    }
}
