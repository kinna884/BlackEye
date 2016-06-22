package com.nonvoid.blackeye.MVP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.adapters.ListViewAdapter;
import com.nonvoid.blackeye.io.InternalStorage;
import com.nonvoid.blackeye.models.Hint;

import java.util.ArrayList;

public class HintListActivity extends AppCompatActivity {
    public final String TAG = "DEBUG_STRING";

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
    }
}
