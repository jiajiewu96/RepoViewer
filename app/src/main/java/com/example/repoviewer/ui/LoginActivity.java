package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.repoviewer.BuildConfig;
import com.example.repoviewer.R;
import com.example.repoviewer.data.model.AccessToken;
import com.example.repoviewer.service.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private String mGithubOauthURL = "https://github.com/login/oauth/authorize";
    private String mGithubClientId = BuildConfig.GITHUB_CLIENT_ID;
    private String mGithubSecret = BuildConfig.GITHUB_SECRET;
    private String callbackUrl = "http://example.com/path/";
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUri = getIntent().getData();
        if(mUri == null) {
            Uri githubOauthUrl = Uri.parse(mGithubOauthURL + "?client_id=" + mGithubClientId + "&scope=repo&redirect_uri=" + callbackUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, githubOauthUrl);
            startActivity(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mUri = getIntent().getData();
        if (mUri != null && mUri.toString().startsWith(callbackUrl)) {
            String code = mUri.getQueryParameter("code");
            buildClientCall(code);
        }

    }

    private void buildClientCall(String code) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);
        Call<AccessToken> accessToken = gitHubClient.getAccessToken(
                mGithubClientId,
                mGithubSecret,
                code
        );

        accessToken.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if(isSuccessfulResponse(response) && response.body().getAccessToken() != null){
                    Toast.makeText(LoginActivity.this, "yay!", Toast.LENGTH_SHORT).show();
                } else{
                    Log.d("Response code", Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "boo!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSuccessfulResponse(Response<AccessToken> response) {
        return response.code() >= 200 && response.code() < 300;
    }
}
