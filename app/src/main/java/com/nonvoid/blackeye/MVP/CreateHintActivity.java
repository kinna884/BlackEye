package com.nonvoid.blackeye.MVP;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.vision.text.Text;
import com.nonvoid.blackeye.R;

import android.Manifest;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.nonvoid.blackeye.MainActivity;
import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.models.Hint;
import com.nonvoid.blackeye.io.InternalStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CreateHintActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "DEBUGSTRING";
    GoogleApiClient mGoogleApiClient = null;
    Hint hint;
    TextView description;
    private Location mLastLocation;
    private TextView mLatitude, mLongitude;
    private LatLng currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hint);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        description = (TextView) findViewById(R.id.editTextHintText);
        mLatitude = (TextView) findViewById(R.id.textViewLatitude);
        mLongitude = (TextView) findViewById(R.id.textViewLongitude);
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void onClick(View v) {
       // Log.d(MainActivity.DEBUGSTR, "Create hint onClick method : " + ((Button) v).getText());
        switch (v.getId()) {
       //     case R.id.buttonGetLocation:

        //      break;
            case R.id.buttonSaveHint:
                TextView HintDescription = (TextView)findViewById(R.id.editTextHintText);
                if (HintDescription.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid entry. Insert a hint before saving.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                else
                {
                    //good to go
                    Hint newHint = new Hint(HintDescription.getText().toString(), currentLocation);
                    ArrayList<Hint> hintList = InternalStorage.readHintList(this);
                    hintList.add(newHint);
                    InternalStorage.writeHintsList(this, hintList);
                    //Log.d(MainActivity.DEBUGSTR, "onClick: saved hintList to InternalStorage");
                    finish();
                }

                /*
                Log.d(MainActivity.DEBUGSTR, text.getText().toString());
                if (hint.center != null && text.getText().length() > 0) {

                    //HintList list = new HintList(this);
                    ArrayList<Hint> list = InternalStorage.readHintList(this);
                    list.add(hint);
                    InternalStorage.writeHintsList(this, list);
                    Log.d(MainActivity.DEBUGSTR, "Finished saving hint");
                    finish();/
                }
                */
                break;


        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            mLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
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

    @Override
    public void onBackPressed() {

        Intent startActivityArmsListViewOnBackPressedIntent = new Intent(this, MainActivity.class);
        startActivity(startActivityArmsListViewOnBackPressedIntent);

        //super.onBackPressed();
    }
}
