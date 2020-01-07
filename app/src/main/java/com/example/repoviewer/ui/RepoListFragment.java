package com.example.repoviewer.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.ui.adapters.RepoListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoListFragment extends Fragment implements RepoListAdapter.RepoClickHandler {

    private Context mContext;
    private RepoListAdapter mRepoListAdapter;
    private TextView mErrorTextView;
    private OnRepoClickListener mOnRepoClickListener;

    public static RepoListFragment newInstance() {
        return new RepoListFragment();
    }

    @Override
    public void onRepoClick(Repository repository) {
        mOnRepoClickListener.onRepoSelected(repository);
    }

    public interface OnRepoClickListener {
        void onRepoSelected(Repository repository);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnRepoClickListener = (OnRepoClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must Implement OnRepoClickListener");
        }
        mContext = getContext();
    }

    public RepoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repo_list, container, false);
        // Inflate the layout for this fragment
        setupRecyclerViews(rootView);
        return rootView;
    }

    private void setupRecyclerViews(View rootView) {
        RecyclerView repoRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_repo_list);
        mErrorTextView = rootView.findViewById(R.id.tv_error);
        mRepoListAdapter = new RepoListAdapter(this);
        LinearLayoutManager repoListLayoutManager = new LinearLayoutManager(getActivity());
        repoRecyclerView.setLayoutManager(repoListLayoutManager);
        repoRecyclerView.setAdapter(mRepoListAdapter);
    }

    public void setRepoList(ArrayList<Repository> repositories) {
        mErrorTextView.setVisibility(View.GONE);
        mRepoListAdapter.setRepositories(repositories);
    }

    public void setErrorTextView(String error) {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(error);
    }


}
