package com.nonvoid.blackeye.MVP;

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

public class CreateHintActivity extends AppCompatActivity  {

    Hint hint;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hint);

        description = (TextView) findViewById(R.id.editTextHintText);


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





}
