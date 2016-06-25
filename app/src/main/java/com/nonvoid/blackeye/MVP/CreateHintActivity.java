package com.nonvoid.blackeye.MVP;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class CreateHintActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "DEBUGSTRING";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1111;

    private GoogleApiClient googleApiClient;

    Hint hint;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hint);

        description = (TextView) findViewById(R.id.editTextHintText);
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: permission not granted");
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
                    PERMISSION_ACCESS_COARSE_LOCATION);
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION"};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, PERMISSION_ACCESS_COARSE_LOCATION);
                
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good![
                    boolean locationAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                    Toast.makeText(this, "Got your location!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private  boolean hasPermission(String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void onClick(View v) {
       // Log.d(MainActivity.DEBUGSTR, "Create hint onClick method : " + ((Button) v).getText());
        switch (v.getId()) {
       //     case R.id.buttonGetLocation:

        //      break;
            case R.id.buttonSaveHint:
                Hint newHint = new Hint(description.getText().toString(), null);
                ArrayList<Hint> hintList = InternalStorage.readHintList(this);
                hintList.add(newHint);
                InternalStorage.writeHintsList(this, hintList);
                //Log.d(MainActivity.DEBUGSTR, "onClick: saved hintList to InternalStorage");
                finish();


                /*
                Log.d(MainActivity.DEBUGSTR, text.getText().toString());
                if (hint.center != null && text.getText().length() > 0) {

                    //HintList list = new HintList(this);
                    ArrayList<Hint> list = InternalStorage.readHintList(this);
                    list.add(hint);
                    InternalStorage.writeHintsList(this, list);
                    Log.d(MainActivity.DEBUGSTR, "Finished saving hint");
                    finish();
                }
                */
                break;


        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
