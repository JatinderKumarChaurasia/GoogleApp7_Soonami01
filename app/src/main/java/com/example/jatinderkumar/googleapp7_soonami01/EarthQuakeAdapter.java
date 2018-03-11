package com.example.jatinderkumar.googleapp7_soonami01;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jatinder Kumar on 10-03-2018.
 */

public class EarthQuakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOG_TAG = EarthQuakeAdapter.class.getSimpleName();
    String primaryLocation;
    String locationOffset;
    private static final String LOCATION_SEPARATOR = "of";

    public EarthQuakeAdapter(@NonNull Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0,earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View earthList = convertView;
        if (earthList == null)
        {
            earthList = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_layout,parent,false);
        }
        Earthquake quake =  getItem(position);
        TextView txtMagnitude = earthList.findViewById(R.id.txtmagnitude);
        TextView txtPlace = earthList.findViewById(R.id.txtPlace);
        TextView txtDest = earthList.findViewById(R.id.txtPlace1);
        TextView txtDate = earthList.findViewById(R.id.txtDate);
        TextView txtTime = earthList.findViewById(R.id.txtTime);
        Date dateObject = new Date(quake.getTimeInMilliseconds());
        String date = formatDate(dateObject);
        String time = formatTime(dateObject);
        txtDate.setText(date);
        String place = quake.getPlace();
        if(place.contains(LOCATION_SEPARATOR))
        {
            String[] parts=  place.split(LOCATION_SEPARATOR);
            locationOffset = parts[0]+LOCATION_SEPARATOR;
            primaryLocation = parts[1];

        }else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = place;
        }
        GradientDrawable drawable = (GradientDrawable)txtMagnitude .getBackground();
        int magnitudeColor = getMagnitudeColor(quake.getMagnitude());
        drawable.setColor(magnitudeColor);
        String magnitude = formatDecimal(quake.getMagnitude());
        txtMagnitude.setText(magnitude);
        txtPlace.setText(locationOffset);
        txtDest.setText(primaryLocation);
        txtTime.setText(time);
        return earthList;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDecimal(double magnitude)
    {
        DecimalFormat formats = new DecimalFormat("0.00");
        return formats.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude)
    {
        int magnitudeColorResourceId = 0;
        int magnitudeColor = (int) Math.floor(magnitude);
        switch (magnitudeColor)
        {
            case 0:
                break;
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

}
