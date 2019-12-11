package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.data.model.User;
import com.example.repoviewer.service.GitHubClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoListActivity extends AppCompatActivity {

    private final String sUserNameKey = "USER_NAME";
    private final String sUserInfoKey = "USER_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(getString(R.string.accessTokenKey))) {

            final AccessToken accessToken = getIntent().getParcelableExtra(getString(R.string.accessTokenKey));
            getUserLogin(accessToken);
            SharedPreferences sharedPreferences = this.getSharedPreferences(sUserInfoKey, MODE_PRIVATE);
            sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                    getRepoList(accessToken);
                }
            });

        }

    }

    private void getUserLogin(AccessToken accessToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", " token " + accessToken.getAccessToken().trim());
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        Call<User> getUser = gitHubClient.getUser(
                headerMap
        );

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    SharedPreferences sharedPreference = getSharedPreferences(sUserInfoKey, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreference.edit();
                    editor.putString(sUserNameKey, response.body().getLogin());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RepoListActivity.this, "boo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRepoList(AccessToken accessToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", " token " + accessToken.getAccessToken().trim());
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        SharedPreferences sharedPreferences = getSharedPreferences(sUserInfoKey, MODE_PRIVATE);
        String username = sharedPreferences.getString(sUserNameKey, "User Not Available");
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
