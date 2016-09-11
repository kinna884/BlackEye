package com.nonvoid.blackeye.Phase1.EventStuff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.models.Event;
import com.nonvoid.blackeye.models.Objective;

public class AddObjectiveActivity extends AppCompatActivity {

    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_objective);
        event = new Event(getIntent().getStringExtra("event"));

        Button addButton = (Button) findViewById(R.id.add_objective_add_bt);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objective objective = new Objective();
                objective.eventId = event.Id;
                objective.latitude = Double.parseDouble (((EditText) findViewById(R.id.add_objective_latitude_et)).getText().toString());
                objective.longitude = Double.parseDouble (((EditText) findViewById(R.id.add_objective_longitude_et)).getText().toString());

                if ( AddObjective(objective) )
                    finish();
            }
        });
    }

    private boolean AddObjective(Objective objective) {
        try{
            if (objective.isValid())
            {
                Objective.addObjective(objective);
                return true;
            }
        }
        catch (VerifyError verifyError){
            Toast.makeText(AddObjectiveActivity.this, verifyError.getMessage(), Toast.LENGTH_LONG).show();

        }
        return false;
    }
}
