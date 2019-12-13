package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.data.model.Repository;
import com.example.repoviewer.data.model.User;
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

public class RepoListActivity extends AppCompatActivity {

    private final FragmentManager sFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        RepoListFragment repoListFragment = RepoListFragment.newInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(Consts.ACCESS_TOKEN_SHAREDPREF_KEY, MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(Consts.ACCESS_TOKEN_KEY, Consts.ACCESS_TOKEN_NULL);
        if(accessToken.equals(Consts.ACCESS_TOKEN_NULL)){
            getUserLogin(accessToken);
        }

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
                if (response.body() != null) {
                    SharedPreferences sharedPreference = getSharedPreferences(Consts.USER_INFO_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreference.edit();
                    editor.putString(Consts.USER_NAME_KEY, response.body().getLogin());
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RepoListActivity.this, "boo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
