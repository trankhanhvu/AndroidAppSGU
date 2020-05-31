package com.example.foodbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        Intent myIntent = getIntent(); // gets the previously created intent
        String foodUrl = myIntent.getStringExtra("foodUrl");

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.loadUrl(foodUrl);
    }
}
