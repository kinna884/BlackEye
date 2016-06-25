package com.nonvoid.blackeye.MVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.models.Hint;

import java.util.ArrayList;

public class CheckHintActivity extends AppCompatActivity {

    ArrayList<Hint> hints;
    public Hint hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_hint);

        Intent intent = getIntent();
        hint = (Hint) intent.getSerializableExtra(HintListActivity.THISHINT);

        TextView hintString = (TextView) findViewById(R.id.hintName);
        hintString.setText(hint.getDescription());

    }
    public void onClick(View v)
    {

        switch (v.getId())

        {
            case R.id.CheckLocationButton:
                Toast toast = Toast.makeText(getApplicationContext(), "This button is under construction. ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 0);
                toast.show();
                break;

        }

    }

}
