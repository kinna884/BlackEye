package com.nonvoid.blackeye.helper;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper implements LocationListener {

    Location location;

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public double getLatitude(){
        return location.getLatitude();
    }
    public double getLongitude(){
        return location.getLongitude();
    }

    public Location location() {
        return location;
    }

}
