package com.nonvoid.blackeye.MVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.io.InternalStorage;
import com.nonvoid.blackeye.models.Hint;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private static final int SUCCESS = 1;
    int CHECKHINTREQUESTCODE = 100;

    ArrayList<Hint> hints = InternalStorage.readHintList(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent i = new Intent(this, CheckHintActivity.class);
        for(int j=0;j<hints.size();j++){
            if(!hints.get(j).isFound())
                continue;
            i.putExtra("HintNumber", j);
            startActivityForResult(i,CHECKHINTREQUESTCODE );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHECKHINTREQUESTCODE){
            switch (requestCode) {
                case SUCCESS :

                    break;
            }
        }
    }
}
