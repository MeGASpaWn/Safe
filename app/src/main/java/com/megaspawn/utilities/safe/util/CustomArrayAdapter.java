package com.megaspawn.utilities.safe.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.megaspawn.utilities.safe.R;
import com.megaspawn.utilities.safe.model.SafeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by varun on 6/1/17.
 */

public class CustomArrayAdapter extends ArrayAdapter<SafeCategory> {

    Context context;

    public CustomArrayAdapter(Context context, List<SafeCategory> categories) {
        super(context, 0, categories);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SafeCategory category = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_row, parent, false);
        }

        // Lookup view for data population
        TextView categoryNameTV = (TextView) convertView.findViewById(R.id.categoryNameTV);
        TextView numItemsTV = (TextView) convertView.findViewById(R.id.numItemsTV);
        ImageView categoryImg = (ImageView) convertView.findViewById(R.id.categoryImg);
        ImageView securityImg = (ImageView) convertView.findViewById(R.id.securityImg);

        // Populate the data into the template view using the data object
        categoryNameTV.setText(category.getName());
        numItemsTV.setText("11 items"); // dummy value for now
        Glide.with(context)
                .load(R.drawable.locked_folder)
                .centerCrop()
                .placeholder(android.R.drawable.spinner_background)
                .fitCenter()
                .crossFade()
                .into(categoryImg);
        Glide.with(context)
                .load(R.drawable.key)
                .centerCrop()
                .placeholder(android.R.drawable.spinner_background)
                .fitCenter()
                .crossFade()
                .into(securityImg);


        // Return the completed view to render on screen
        return convertView;
    }

}
