package com.example.android.marwari;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AV JAYARAMAN on 28-Jul-16.
 */
public class CustomAdapter extends ArrayAdapter<Word> {

    private int mLayoutColor;
    private MediaPlayer mMediaPlayer;

    public CustomAdapter(Context context, List<Word> words, int color) {
        super(context, 0, (List<Word>) words);
        mLayoutColor = color;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Get views from layout inflater
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.list_item, null);

        // adding values to textviews
        TextView miwokText = (TextView) view.findViewById(R.id.miwok);
        TextView defaultText = (TextView) view.findViewById(R.id.default_text);
        miwokText.setText(getItem(position).getMiwokTranslation());
        defaultText.setText(getItem(position).getDefaultTranslation());

        //Adding images to categories provided there are
        ImageView sideImage = (ImageView) view.findViewById(R.id.side_img);
        if (getItem(position).hasImage()) {
            sideImage.setImageResource(getItem(position).getImgResource());
        } else {
            sideImage.setVisibility(View.GONE);
        }


        //Adding custom color to each category
        LinearLayout textLayout = (LinearLayout) view.findViewById(R.id.text_layout);
        textLayout.setBackgroundResource(mLayoutColor);

        return view;
    }

}
