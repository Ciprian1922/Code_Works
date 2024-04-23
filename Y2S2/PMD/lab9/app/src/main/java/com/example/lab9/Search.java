package com.example.lab9;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class Search extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        webView = new WebView(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
                    performSearch(query);
                }
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void performSearch(String query) {
        String searchUrl = "https://www.google.com/search?q=" + Uri.encode(query);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setContentView(webView);
        webView.loadUrl(searchUrl);
    }
}
