package com.nonvoid.blackeye.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Matt on 6/21/2016.
 */
public class Hint {
    String description;
    LatLng location;

    public Hint(String description, LatLng location) {
        this.description = description;
        this.location = location;
    }

    public Hint(String description) {
        this.description = description;
        this.location = new LatLng(0,0);
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLocation() {
        return location;
    }
}
