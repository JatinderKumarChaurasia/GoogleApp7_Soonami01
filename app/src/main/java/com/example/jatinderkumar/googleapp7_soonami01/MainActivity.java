package com.example.jatinderkumar.googleapp7_soonami01;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>>{
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";
    ListView list;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private EarthQuakeAdapter adapter;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        emptyView = findViewById(R.id.text);
        list.setEmptyView(emptyView);
        adapter = new EarthQuakeAdapter(MainActivity.this,new ArrayList<Earthquake>());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake earthquake = adapter.getItem(position);
                Uri earthQuakeUri = Uri.parse(earthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,earthQuakeUri);
                startActivity(intent);
            }
        });

       // EarthquakeAsyncTask task = new EarthquakeAsyncTask();
       // task.execute(USGS_REQUEST_URL);
        ConnectivityManager connect = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connect.getActiveNetworkInfo();
        if (info != null && info.isConnected())
        {
            LoaderManager manager = getLoaderManager();
            manager.initLoader(EARTHQUAKE_LOADER_ID,null,this);
        }else {
            View loadingIndicator = findViewById(R.id.progress);
            loadingIndicator.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i("OnCreateLoaderFinished", "OnCreateLoader is Called");
        return new EarthquakeLoader(MainActivity.this, USGS_REQUEST_URL);


    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        View loadingIndicator = findViewById(R.id.progress);
        loadingIndicator.setVisibility(View.GONE);
        emptyView.setText("No Messages");
        adapter.clear();
       if (data != null && !data.isEmpty())
        {
           adapter.addAll(data);
        }

        Log.i("LoaderFinished","Loader Finished is Called");
    }
    @Override
    public void onLoaderReset(Loader loader) {
        Log.i("OnLoaderReset","Loader Reset is Called");
        adapter.clear();
    }

   /* private class EarthquakeAsyncTask extends AsyncTask<String,Void,List<Earthquake>>
    {
        @Override
        protected List<Earthquake> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null)
            {
                return null;
            }
            List<Earthquake> earthquakes = QueryUtils.fetchDataFromUrl(strings[0]);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            adapter.clear();
            if (earthquakes!= null && !earthquakes.isEmpty())
            {
                adapter.addAll(earthquakes);
            }
        }
    }*/
}
