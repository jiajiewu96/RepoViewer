package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.data.model.User;
import com.example.repoviewer.service.GitHubClient;
import com.example.repoviewer.utils.Consts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoListActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private final FragmentManager sFragmentManager = getSupportFragmentManager();
    private SharedPreferences mUserInfoSharedPreference;
    private String mAccessToken;
    private RepoListFragment mRepoListFragment;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        mRepoListFragment = RepoListFragment.newInstance();
        SharedPreferences accessTokenSharedPreference = getSharedPreferences(Consts.ACCESS_TOKEN_SHAREDPREF_KEY, MODE_PRIVATE);
        mUserInfoSharedPreference = getSharedPreferences(Consts.USER_INFO_KEY, MODE_PRIVATE);
        mAccessToken = accessTokenSharedPreference.getString(Consts.ACCESS_TOKEN_KEY, Consts.ACCESS_TOKEN_NULL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUserInfoSharedPreference.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mAccessToken.equals(Consts.ACCESS_TOKEN_NULL)) {
            sFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(R.id.repo_list_container, mRepoListFragment)
                    .commit();
            getUserLogin(mAccessToken);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUserInfoSharedPreference.unregisterOnSharedPreferenceChangeListener(this);

    }


    private void getUserLogin(String accessToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", " token " + accessToken.trim());
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        Call<User> getUser = gitHubClient.getUser(
                headerMap
        );

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null && response.isSuccessful()) {
                    mEditor = mUserInfoSharedPreference.edit();
                    mEditor.clear();
                    mEditor.putString(Consts.USER_NAME_KEY, response.body().getLogin());
                    mEditor.apply();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RepoListActivity.this, "boo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRepoList(String accessToken) {
        String username = mUserInfoSharedPreference.getString(Consts.USER_NAME_KEY, Consts.USER_NOT_AVAILABLE);
        if(!username.equals(Consts.USER_NOT_AVAILABLE) && username != null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Authorization", " token " + accessToken.trim());
            GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
            Call<List<Repository>> getUserRepository = gitHubClient.reposForUser(
                    username
            );
            getUserRepository.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    ArrayList<Repository> repositories;
                    if (response.body() != null) {
                        repositories = (ArrayList<Repository>) response.body();
                        mRepoListFragment.setRepoList(repositories);
                    }
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    mRepoListFragment.setErrorTextView("There was an error retrieving your username");
                }
            });
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(Consts.USER_NAME_KEY)) {
            if (!mAccessToken.equals(Consts.ACCESS_TOKEN_NULL)) {
                getRepoList(mAccessToken);
            }
        }
    }
}
