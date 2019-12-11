package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.repoviewer.R;
import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.data.model.User;
import com.example.repoviewer.service.GitHubClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        AccessToken accessToken = null;
        if(getIntent().getExtras() != null && getIntent().getExtras().containsKey(getString(R.string.accessTokenKey))) {
           accessToken = getIntent().getParcelableExtra(getString(R.string.accessTokenKey));
           getUserLogin(accessToken);
           getRepoList(accessToken);
        }

    }

    private void getUserLogin(AccessToken accessToken) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", " token "+accessToken.getAccessToken().trim());
        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        Call<User> getUser = gitHubClient.getUser(
            headerMap
        );

        getUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RepoListActivity.this, "boo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
