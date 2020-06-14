package com.example.nationinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.InputStream;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail);

        String id = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             id = bundle.getString("id");
        }

        final List<ItemList> list_country = MainActivity.list;
        Log.i("res", String.valueOf(list_country));
        assert id != null;
        ItemList country = list_country.get(Integer.parseInt(id));

        TextView countryName = (TextView) findViewById(R.id.countryName);
        ImageView countryFlag = (ImageView) findViewById(R.id.countryFlag);
        TextView population = (TextView) findViewById(R.id.population);
        TextView areaKm = (TextView) findViewById(R.id.areaKm);

        countryName.setText(country.getCountryName());
        new DownloadImageTask((ImageView) findViewById(R.id.countryFlag))
                .execute(country.getCountryFlag());
        population.setText(Double.toString(country.getPopulation()));
        areaKm.setText(Integer.toString((int) country.getAreaKm()));
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
