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
 * Created by Matt on 6/21/2016.
 */
public class ListViewAdapter extends ArrayAdapter<Hint> {
    public ListViewAdapter(Context context, ArrayList<Hint> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hint item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_row, parent, false);
        }

        long date = System.currentTimeMillis();
        TextView dateTime = (TextView) convertView.findViewById(R.id.dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        dateTime.setText(dateString);

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.hintDescription);
        TextView loc = (TextView) convertView.findViewById(R.id.hintLocation);

        //title.setText(item.getDescription());
        //loc.setText(item.getLocation().toString());


        return convertView;
    }
}
