package com.nonvoid.blackeye.Phase1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nonvoid.blackeye.BuildConfig;
import com.nonvoid.blackeye.R;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

public class SplashScreen extends Activity {
    private final int SPLASH_TIME = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);
        splash();
    }
    public void splash(){
        new Handler().postDelayed(new Runnable() {

            //Show splash screen for SPLASH_TIME length
            @Override
            public void run() {
                // Start app main activity after splash minimal time
                endSplash();
            }
        }, SPLASH_TIME);
    }

    public void endSplash(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            // not signed in
            // Note: SmartLock Passwords are disabled in Debug Builds
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.FACEBOOK_PROVIDER)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                endSplash();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
                // for now I'm just having it re-run endSplash() and have it ask for Auth again
                endSplash();
            }
        }
    }

    private void checkIfUserIsContentAdmin(final String email){
        DatabaseReference AuthDb = FirebaseDatabase.getInstance().getReference().child("contentadmins");
        Query isAuthorized = AuthDb;
        isAuthorized.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot adminSnapshot:  dataSnapshot.getChildren()) {
                    String adminEmail = adminSnapshot.getValue(String.class);
                    if(adminEmail.equals(email)){
                        // Store admin variable
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("onCancelled", "Failed to read value.", databaseError.toException());
            }
        });
    }

    }
