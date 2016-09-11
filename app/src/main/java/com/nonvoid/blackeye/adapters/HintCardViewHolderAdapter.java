package com.nonvoid.blackeye.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nonvoid.blackeye.R;

/**
 * Created by Matt on 9/10/2016.
 */

public class HintCardViewHolderAdapter extends RecyclerView.ViewHolder {
    View mView;
    public HintCardViewHolderAdapter(View itemView) {
        super(itemView);
        mView = itemView;
    }
    public void setTitle(String title){
        TextView field = (TextView) mView.findViewById(R.id.hint_cardview_title);
        field.setText(title);
    }
    public void setBody(String body){
        TextView field = (TextView) mView.findViewById(R.id.hint_cardview_body);
        field.setText(body);
    }
}
