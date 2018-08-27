package com.freelance.yahia.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class xmlservice extends AppCompatActivity {

    static int i = 0;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> value = new ArrayList<>();

    private class GetXml extends AsyncTask<URL, Integer, XmlPullParser>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected XmlPullParser doInBackground(URL... urls) {
            XmlPullParser parser = Xml.newPullParser();
            try {
                HttpURLConnection conn = (HttpURLConnection) urls[0].openConnection();
                InputStream in = conn.getInputStream();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

                parser.next();
                do {
                    if(parser.getEventType()== XmlPullParser.START_TAG)
                    {
                        String name = parser.getName();
                        list.add(name);
                    }
                    else if(parser.getEventType() == XmlPullParser.TEXT)
                    {
                        String text = parser.getText();
                        value.add(text);
                    }
                }while (parser.nextToken() != XmlPullParser.END_DOCUMENT);
            }catch (Exception e){Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();}

            return parser;
        }

        @Override
        protected void onPostExecute(XmlPullParser parser) {
            super.onPostExecute(parser);
            try {
                Toast.makeText(getApplicationContext(), String.valueOf(list.size()), Toast.LENGTH_LONG).show();
                for (int i = 0; i < list.size(); i++) {
                    Toast.makeText(getApplicationContext(), list.get(i), Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();}

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlservice);
        try {
            URL url = new URL("http://api.geonames.org/children?geonameId=3175395&username=demo");
            new GetXml().execute(url);
        }catch (Exception e){}

    }
}
