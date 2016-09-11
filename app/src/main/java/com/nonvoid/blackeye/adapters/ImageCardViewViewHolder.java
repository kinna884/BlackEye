package com.nonvoid.blackeye.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonvoid.blackeye.R;

/**
 * Created by Jacob on 9/3/2016.
 */

public class ImageCardViewViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ImageCardViewViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setName(String name) {
            TextView field = (TextView) mView.findViewById(R.id.image_cardview_title);
            field.setText(name);
        }

        public void setDate(String text) {
            TextView field = (TextView) mView.findViewById(R.id.image_cardview_date);
            field.setText(text);
        }

        public void isFavorite(Boolean fav){
            if(fav) {
                ImageView icon = (ImageView) mView.findViewById(R.id.image_cardview_fav_iv);
                icon.setImageResource(R.mipmap.fav_liked);
            }
        }
}