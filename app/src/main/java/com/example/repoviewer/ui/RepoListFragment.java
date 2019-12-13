package com.example.repoviewer.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.service.GitHubClient;
import com.example.repoviewer.utils.Consts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RepoListFragment extends Fragment {

    private Context mContext;

    public static RepoListFragment newInstance(){
        return new RepoListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getContext();
    }

    public RepoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Consts.USER_INFO_KEY, MODE_PRIVATE);
        final SharedPreferences accessTokenPref = mContext.getSharedPreferences(Consts.ACCESS_TOKEN_KEY, MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String accessToken = accessTokenPref.getString(Consts.ACCESS_TOKEN_KEY, Consts.ACCESS_TOKEN_NULL);
                if(!accessToken.equals(Consts.ACCESS_TOKEN_NULL)) {
                    getRepoList(accessToken);
                }
            }
        });
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    private void getRepoList(String accessToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", " token " + accessToken.trim());
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Consts.USER_INFO_KEY, MODE_PRIVATE);
        String username = sharedPreferences.getString(Consts.USER_NAME_KEY, "User Not Available");
        Call<List<Repository>> getUserRepository = gitHubClient.reposForUser(
                username
        );
        getUserRepository.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {

            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }

}
