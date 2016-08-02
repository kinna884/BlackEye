package com.nonvoid.blackeye.Phase1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.adapters.ListViewAdapter;
import com.nonvoid.blackeye.io.InternalStorage;
import com.nonvoid.blackeye.models.Hint;

import java.util.ArrayList;

public class HintListActivity extends AppCompatActivity {
    public final String TAG = "DEBUG_STRING";
    public final static String THISHINT = "THISHINT";

    ArrayList<Hint> hints;
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint_list);
        hints = InternalStorage.readHintList(this);
        Log.d(TAG, "onCreate: hints size="+hints.size());
        listView = (ListView) findViewById(R.id.listViewHints);
        adapter = new ListViewAdapter(this, hints);
        listView.setAdapter(adapter);


        hints = InternalStorage.readHintList(this);

        if (hints.size() == 0)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "No hints have been added yet. ", Toast.LENGTH_LONG);

            toast.setGravity(Gravity.BOTTOM | Gravity.BOTTOM, 0, 0);
            toast.show();

        }


        Log.d(TAG, "onCreate: hints size=" + hints.size());
        listView = (ListView) findViewById(R.id.listViewHints);
        adapter = new ListViewAdapter(this, hints);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(HintListActivity.this, CheckHintActivity.class);
                        intent.putExtra(THISHINT, hints.get(position));
                        startActivityForResult(intent, position);
                    }
                }

        );
    }

    @Override
    public void onBackPressed() {

        Intent startActivityArmsListViewOnBackPressedIntent = new Intent(this, MainActivity.class);
        startActivity(startActivityArmsListViewOnBackPressedIntent);

        //super.onBackPressed();
    }

}
