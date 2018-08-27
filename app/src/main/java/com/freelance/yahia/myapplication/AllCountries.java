package com.freelance.yahia.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AllCountries extends AppCompatActivity {

    static TextView textView;
    static ListView listView;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayList<String> alpha2 = new ArrayList<>();
    static ArrayList<String> alpha3 = new ArrayList<>();
    static ArrayList<String> itemsC = new ArrayList<>();
    static ArrayList<String> alpha2C = new ArrayList<>();
    static ArrayList<String> alpha3C = new ArrayList<>();
    static ListAdapter listAdapter;
    static int k;
    static int flag = 0;

    @TargetApi(21)
            void addListener(ConnectivityManager connectivityManager){

        connectivityManager.addDefaultNetworkActiveListener(new  ConnectivityManager.OnNetworkActiveListener() {
            @Override
            public void onNetworkActive() {
           //     try {
                    Toast.makeText(getApplicationContext(), "e.toString()", Toast.LENGTH_LONG).show();
              //      new GetJsonArray().execute(url);
           //     }catch (Exception e){
              //      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
             //   }

            }

        });
    }

    private class GetJsonArray extends AsyncTask<URL, Integer, String>{

        @Override
        protected String doInBackground(URL... urls) {
            try {
            HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
            InputStream in = urlConnection.getInputStream();


                    BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line);
                    }

                    in.close();
                    String fileString = sBuilder.toString();



                return fileString;
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.toString() + "third", Toast.LENGTH_LONG).show();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String fileString) {
            super.onPostExecute(fileString);
            try {

                JSONObject jsonAll = new JSONObject(fileString);
                JSONObject jsonObject = jsonAll.getJSONObject("RestResponse");
                JSONArray jsonArray = jsonObject.getJSONArray("messages");
                String massege = jsonArray.getString(0);
                textView.setText(massege);
                k = jsonObject.getJSONArray("result").length();
                for(int i = 0; i < k; i++){
                    items.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("name"));
                    alpha2.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("alpha2_code"));
                    alpha3.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("alpha3_code"));
                    itemsC.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("name"));
                    alpha2C.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("alpha2_code"));
                    alpha3C.add(jsonObject.getJSONArray("result").getJSONObject(i).getString("alpha3_code"));
                }

                listView.setAdapter(listAdapter);

            }catch (Exception e){Toast.makeText(getApplicationContext(), e.toString() + "first", Toast.LENGTH_LONG).show();}
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_countries);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null) {
        if(networkInfo.isConnected()) {
            items.clear();
            alpha2.clear();
            alpha3.clear();
            itemsC.clear();
            alpha2C.clear();
            alpha3C.clear();
            textView = findViewById(R.id.counts);
            listView = findViewById(R.id.listView);
            final EditText search = findViewById(R.id.search);
            search.setText("");

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    try {
                        String result = editable.toString();

                        itemsC.clear();
                        alpha2C.clear();
                        alpha3C.clear();

                        for (int i = 0; i < items.size(); i++) {

                            if ((items.get(i).contains(result))) {
                                itemsC.add(items.get(i));
                                alpha2C.add(alpha2.get(i));
                                alpha3C.add(alpha3.get(i));
                            }

                        }

                        listView.removeAllViewsInLayout();
                        listView.setAdapter(listAdapter);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            listAdapter = new ListAdapter() {
                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                public boolean isEnabled(int i) {
                    return false;
                }

                @Override
                public void registerDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

                }

                @Override
                public int getCount() {
                    return itemsC.size();
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public boolean hasStableIds() {
                    return false;
                }

                @Override
                public View getView(final int i, View view, ViewGroup viewGroup) {

                    final LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.list_single, null, true);
                    TextView t = rowView.findViewById(R.id.item_Text);
                    TextView a2 = rowView.findViewById(R.id.alpha2);
                    TextView a3 = rowView.findViewById(R.id.alpha3);
                    t.setText(itemsC.get(i));
                    a2.setText("  alpha2 code: " + alpha2C.get(i));
                    a3.setText("  /  alpha3 code: " + alpha3C.get(i));

                    rowView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(flag == 0){
                            Intent intent = new Intent(AllCountries.this, CountryDetails.class);
                            intent.putExtra("country", itemsC.get(i));
                            startActivity(intent);
                        }
                        else flag = 0;
                        }
                    });
                    rowView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            flag = 1;
                            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                            if(networkInfo != null) {
                                if(networkInfo.isConnected()) {
                                    Intent intent = new Intent(AllCountries.this, xmlservice.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_LONG).show();

                            return false;
                        }
                    });
                    return rowView;
                }

                @Override
                public int getItemViewType(int i) {
                    return 0;
                }

                @Override
                public int getViewTypeCount() {
                    return itemsC.size();
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }
            };

            try {

                final URL url = new URL("http://services.groupkt.com/country/get/all");

                if (cm.getActiveNetworkInfo() != null)
                    new GetJsonArray().execute(url);
                else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection 1", Toast.LENGTH_LONG).show();
                    addListener(cm);
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString() + "sec", Toast.LENGTH_LONG).show();
            }
        }
        else
        {Toast.makeText(getApplicationContext(), "No Internet Access 2", Toast.LENGTH_LONG).show();
            addListener(cm);}


    }
        else
        {Toast.makeText(getApplicationContext(), "No Internet Access 3", Toast.LENGTH_LONG).show();
        addListener(cm);}
    }

}
