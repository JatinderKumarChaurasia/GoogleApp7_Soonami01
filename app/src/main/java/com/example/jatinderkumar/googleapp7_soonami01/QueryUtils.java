package com.example.jatinderkumar.googleapp7_soonami01;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jatinder Kumar on 10-03-2018.
 */

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    private static URL createUrl(String requestUrl)
    {
        URL url = null;
        try {
            url = new URL(requestUrl);
        }catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL requestUrl) throws IOException {
        String jsonResponse = "";
        if (requestUrl == null)
        {
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();
            if (connection.getResponseCode() == 200)
            {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (connection != null)
            {
                connection.disconnect();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Earthquake> extractDataFromJson(String requestUrl)
    {
        if (TextUtils.isEmpty(requestUrl))
        {
            return  null;
        }
        List<Earthquake> earthquakes = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(requestUrl);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                Earthquake earthquake = new Earthquake(location,magnitude,time, url);
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

    public static List<Earthquake> fetchDataFromUrl(String requestUrl)
    {
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e)
        {
            Log.e("Interupted Exception","It is Interupted");
        }
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Earthquake> earthquakes = extractDataFromJson(jsonResponse);
        return earthquakes;
    }
}
