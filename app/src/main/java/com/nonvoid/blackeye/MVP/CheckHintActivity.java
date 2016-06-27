package com.nonvoid.blackeye.MVP;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.models.Hint;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckHintActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "DEBUGSTRING" ;
    GoogleApiClient mGoogleApiClient = null;
    public Hint hint;
    private Location mLastLocation;
    private LatLng currentLocation;
    private double MARGIN_OF_ERROR = 25.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hint);

        //setup GoogleApiClient
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Intent intent = getIntent();
        hint = (Hint) intent.getSerializableExtra(HintListActivity.THISHINT);

        TextView hintString = (TextView) findViewById(R.id.hintName);
        hintString.setText(hint.getDescription());

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onClick(View v)
    {

        switch (v.getId())

        {
            case R.id.CheckLocationButton:

                mGoogleApiClient.connect();

                Location curLoc = new Location("");
                curLoc.setLatitude(currentLocation.latitude);
                curLoc.setLongitude(currentLocation.longitude);

                Location hintLoc = new Location("");
                hintLoc.setLatitude(hint.getLat());
                hintLoc.setLongitude(hint.getLng());

                double distance = curLoc.distanceTo(hintLoc);

                // -----------------------------------------------------------
                // Testing distance remove Text View later
                TextView distancetoLocation = (TextView)findViewById(R.id.distanceTo);
                DecimalFormat df = new DecimalFormat("####.##");
                String tmpDistance = String.valueOf(df.format(distance));
                distancetoLocation.setText(tmpDistance + " meters away");
               //-------------------------------------------------------------------

                Log.d(TAG, "onClick: distance: "+distance);
                if(distance < MARGIN_OF_ERROR){
                    Toast toast = Toast.makeText(getApplicationContext(), "You found it!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 0);
                    toast.show();
                    break;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Not close enough", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 0);
                toast.show();
                break;

        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "onConnected failed self permissions");
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d(TAG, "onConnected: mLastLocation != null");
            currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
