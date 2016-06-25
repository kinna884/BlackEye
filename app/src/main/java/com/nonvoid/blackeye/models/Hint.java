package com.nonvoid.blackeye.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Matt on 6/21/2016.
 */
public class Hint implements Serializable {
    String description;
    //LatLng location;
    double lat,lng;

    public Hint(String description, LatLng location) {
        this.description = description;
        this.lat = location.latitude;
        this.lng = location.longitude;
    }

    public Hint(String description) {
        this.description = description;
        //this.location = new LatLng(0,0);
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLocation() {
        return new LatLng(lat, lng);
    }

    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }
}
