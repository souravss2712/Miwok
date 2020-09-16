package com.example.miwok;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Word} objects.
 */
public class WordAdapter extends ArrayAdapter<Word>  {

    private int colorid;

    public WordAdapter(Activity context, ArrayList<Word> words, int color) {
        super(context, 0, words);
        colorid=color;
    }// passing zero for layout resource id ,  because we are inflating view ourselves in the getview method

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final Word currentword = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentword.getMdefault());
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentword.getMmiwok());

        ImageView imgview=(ImageView) listItemView.findViewById(R.id.img_view);
        if(currentword.hasimage()) {
            imgview.setImageResource(currentword.getimage());
            imgview.setVisibility(View.VISIBLE);
        }
        else
            imgview.setVisibility(View.GONE);
        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView

        // Set the theme color for the list item

        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), colorid);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);



        return listItemView;

    }
}