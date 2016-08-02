package com.nonvoid.blackeye.Phase1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nonvoid.blackeye.Phase1.CreateHintActivity;
import com.nonvoid.blackeye.Phase1.HintListActivity;
import com.nonvoid.blackeye.Phase1.PlayActivity;
import com.nonvoid.blackeye.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                Intent i2 = new Intent(this, PlayActivity.class);
                startActivity(i2);
                break;

        }
    }


}

