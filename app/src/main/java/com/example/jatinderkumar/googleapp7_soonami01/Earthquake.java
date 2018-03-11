package com.example.jatinderkumar.googleapp7_soonami01;

/**
 * Created by Jatinder Kumar on 10-03-2018.
 */

public class Earthquake {
    String place;
    double magnitude;
    Long timeInMilliseconds;
    String url;

    public Earthquake(String place, double magnitude, Long timeInMilliseconds, String url) {
        this.place = place;
        this.magnitude = magnitude;
        this.timeInMilliseconds = timeInMilliseconds;
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public Long getTimeInMilliseconds() {
        return timeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }
}
