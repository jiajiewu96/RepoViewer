package com.example.repoviewer.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.repoviewer.BuildConfig;
import com.example.repoviewer.R;

public class LoginActivity extends AppCompatActivity {

    private String mGithubOauthURL = "https://github.com/login/oauth/authorize";
    private String mGithubClientId = BuildConfig.GITHUB_CLIENT_ID;
    private String mGithubSecret = BuildConfig.GITHUB_SECRET;
    private String callbackUrl = "http://example.com/someresource/";
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUri = getIntent().getData();
        if (mUri == null || !mUri.toString().startsWith(callbackUrl)) {
            Uri githubOauthUrl = Uri.parse(mGithubOauthURL+"?client_id="+mGithubClientId+"&scope=repo&redirect_uri="+callbackUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, githubOauthUrl);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUri == null || !mUri.toString().startsWith(callbackUrl)) {
            mUri = getIntent().getData();
        }
        if( mUri != null && mUri.toString().startsWith(callbackUrl)){
            String code = mUri.getQueryParameter("code");
        }
    }
}
