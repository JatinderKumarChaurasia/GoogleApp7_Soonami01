package com.example.jatinderkumar.googleapp7_soonami01;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jatinder Kumar on 10-03-2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String url;

    public EarthquakeLoader(Context context,String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i("Start Loading","Loading is Started");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.i("Load in Background","Loading in Background");
        if (url == null)
        {
            return null;
        }
        List<Earthquake> earthquakes = QueryUtils.fetchDataFromUrl(url);
        return earthquakes;
    }
}
