package com.example.jason.nytimessearch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.jason.nytimessearch.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String url = getIntent().getStringExtra("url");
        WebView webView = (WebView) findViewById(R.id.wvArticle);

        webView.loadUrl(url);
    }
}
