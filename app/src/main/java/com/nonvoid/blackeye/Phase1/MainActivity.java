package com.nonvoid.blackeye.Phase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nonvoid.blackeye.Phase1.AppStuff.SplashScreen;
import com.nonvoid.blackeye.Phase1.EventStuff.CreateHintActivity;
import com.nonvoid.blackeye.Phase1.EventStuff.EventListActivity;
import com.nonvoid.blackeye.Phase1.EventStuff.HintListActivity;
import com.nonvoid.blackeye.Phase1.EventStuff.LocationsToDiscoverActivity;
import com.nonvoid.blackeye.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Set welcome message
            TextView welcomeMsg = (TextView)findViewById(R.id.welcome_textView);
            welcomeMsg.setText("Welcome " + user.getDisplayName());
        }

        //Set ViewListeners
        Button eventButton = (Button)findViewById(R.id.view_events_button);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventListActivity.class));
            }
        });

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.createHintButton :
                Intent i = new Intent(this, CreateHintActivity.class);
                startActivity(i);
                break;

            case  R.id.viewHintsButton :
                Intent i1 = new Intent(this, HintListActivity.class);
                startActivity(i1);
                break;
            case  R.id.playButton:
                Intent i2 = new Intent(this, LocationsToDiscoverActivity.class);
                startActivity(i2);
                break;
            case R.id.logout_button:
                Log.d("quest", "Logout pressed");
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(MainActivity.this, SplashScreen.class));
                                finish();
                            }
                        });
                break;
        }
    }


}

