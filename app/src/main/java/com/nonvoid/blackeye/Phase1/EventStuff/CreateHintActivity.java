package com.nonvoid.blackeye.Phase1.EventStuff;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nonvoid.blackeye.Phase1.MainActivity;
import com.nonvoid.blackeye.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.nonvoid.blackeye.helper.ImageHelper;
import com.nonvoid.blackeye.models.Hint;
import com.nonvoid.blackeye.io.InternalStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateHintActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "DEBUGSTRING";
    GoogleApiClient mGoogleApiClient = null;
    Hint hint;
    TextView description;
    private Location mLastLocation;
    private TextView mLatitude, mLongitude;
    private LatLng currentLocation;
    private ImageHelper imageFromCam;


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImageHelper.IMAGE_REQUEST_CODE) {
            if(resultCode == RESULT_OK)
            {
                if(this.imageFromCam != null) {
                    //item.addImage(imageFromCam);
                    Toast.makeText(this, "Image Saved", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Error Saving Image", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Image Not Added", Toast.LENGTH_LONG).show();
                imageFromCam = null;
            }
        }
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
                    Hint newHint = new Hint(HintDescription.getText().toString());
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
            case R.id.buttonTakePicture :
                Log.d(TAG, "onclick buttonTakePicture");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    this.imageFromCam = new ImageHelper();
                    if(this.imageFromCam == null)
                        Log.d(TAG, "onClick: imageFromCam is null");
                    try {
                        photoFile = imageFromCam.createImageFile();
                        Log.d(TAG, "Create image file success");
                    } catch (IOException ex) {
                        Log.d(TAG, "Create image file error");
                    }
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, ImageHelper.IMAGE_REQUEST_CODE);
                    }
                }
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
