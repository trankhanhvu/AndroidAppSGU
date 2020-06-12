package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItemList> list_country = null;
        list_country = getListCountry();
        final ListView listView = (ListView) findViewById(R.id.listCountry);
        listView.setAdapter(new CustomListAdapter(this, list_country));


    }

    private List<ItemList> getListData() {
        List<ItemList> items = new ArrayList<ItemList>();

        return items;
    }

    public List<ItemList> getListCountry(){
        final List<ItemList> items = new ArrayList<ItemList>();

        ItemList item = new ItemList("Country Code","Country Name","Capital",0,0);
        items.add(item);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://api.geonames.org/countryInfoJSON?formatted=true&lang=en&username=boykunis&style=full", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response=new JSONObject(new String(responseBody));

                    String countryCode = "",countryName = "",capital = "";
                    int population = 0;
                    double areaInSqKm = 0;

                    JSONArray result = response.getJSONArray("geonames");

                    for(int i =0; i<result.length();i++){
                        countryCode=result.getJSONObject(i).getString("countryCode");
                        countryName=result.getJSONObject(i).getString("countryName");
                        capital=result.getJSONObject(i).getString("capital");
                        population=result.getJSONObject(i).getInt("population");
                        areaInSqKm=result.getJSONObject(i).getDouble("areaInSqKm");

                        ItemList item = new ItemList(countryCode,countryName,capital,population,areaInSqKm);
                        items.add(item);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }
        });
        return items;
    }
}



