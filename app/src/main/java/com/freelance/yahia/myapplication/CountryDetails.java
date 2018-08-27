package com.freelance.yahia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class CountryDetails extends AppCompatActivity {
    static WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        String country = i.getStringExtra("country");
        final TextView mTextView = findViewById(R.id.details);
        String url = "https://en.wikipedia.org/wiki/" + country;
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);



    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
       else
           super.onBackPressed();
    }
}
