package com.nonvoid.blackeye.Phase1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.dialog.DatePickerFragment;
import com.nonvoid.blackeye.models.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListener{
    private static final String DIALOG_DATE = "AddEventActivity.DateDialog";

    EditText et_name;
    EditText et_description;
    Button bt_date;
    TextView tv_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //Bind view elements
        et_name = (EditText)findViewById(R.id.new_event_name);
        et_description = (EditText)findViewById(R.id.new_event_description);
        tv_date = (TextView) findViewById(R.id.new_event_select_date_tv);
        bt_date = (Button) findViewById(R.id.new_event_select_date_bt);
        bt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), DIALOG_DATE);
            }
        });
        Button submit = (Button)findViewById(R.id.new_event_submit_bt);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event newEvent = new Event();
                newEvent.name = et_name.getText().toString();
                newEvent.description = et_description.getText().toString();
                newEvent.date = tv_date.getText().toString();
                verifyEvent(newEvent);
            }
        });
    }
    @Override
    public void onFinishDialog(Date date) {
        Toast.makeText(this, "Selected Date : "+ formatDate(date), Toast.LENGTH_SHORT).show();
        tv_date.setText(formatDate(date));
    }
    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String eventDate = sdf.format(date);
        return eventDate;
    }

    private void verifyEvent(Event event){
        try {
            if(event.isValid()) {
                Log.d("AddEvent", "Event Valid");
                Event.AddEvent(event);
                finish();
            }
        }
        catch(VerifyError e){
            Log.d("AddEvent", "Event Valid");
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
