package com.freelance.yahia.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button all_countries = findViewById(R.id.button2);
        all_countries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if(networkInfo != null) {
                    if(networkInfo.isConnected()) {
              Intent intent = new Intent(MainActivity.this, AllCountries.class);
                startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
