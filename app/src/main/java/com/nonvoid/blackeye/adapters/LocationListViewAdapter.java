package com.nonvoid.blackeye.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nonvoid.blackeye.R;
import com.nonvoid.blackeye.models.Hint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Quinten on 8/1/2016.
 */
public class LocationListViewAdapter extends ArrayAdapter<Hint> {
    public LocationListViewAdapter(Context context, ArrayList<Hint> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hint item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.locationstodiscoverview, parent, false);
        }


        int stageNum = 1;
        TextView stageNumber = (TextView) convertView.findViewById(R.id.stageNumber);
        stageNumber.setText("Stage " + stageNum);

        return convertView;

    }
}
