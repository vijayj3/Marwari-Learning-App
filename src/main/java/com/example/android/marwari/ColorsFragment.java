package com.example.android.marwari;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.marwari.Word;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    private ArrayList<Word> words;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudio;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case (AudioManager.AUDIOFOCUS_GAIN):
                    mMediaPlayer.seekTo(0);
                case (AudioManager.AUDIOFOCUS_LOSS):
                    releaseMediaPlayer();
                case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                case (AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK):
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
            }
        }
    };

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.word_list,container,false);

        wordsPopulate();
        populateLayout();

        return rootView;
    }

    public void wordsPopulate() {
        words = new ArrayList<Word>();

        words.add(new Word("red", "laal", R.raw.color_red, R.drawable.color_red));
        words.add(new Word("green", "hariya", R.raw.color_green, R.drawable.color_green));
        words.add(new Word("black", "kaalo", R.raw.color_black, R.drawable.color_black));
        words.add(new Word("white", "dhavlo", R.raw.color_white, R.drawable.color_white));
        words.add(new Word("yellow", "peelo", R.raw.color_yellow, R.drawable.color_mustard_yellow));
        /*words.add(new Word("saffron", "kesar", R.raw.color_brown, R.drawable.color_brown));
        words.add(new Word("blue", "asmoni", R.raw.color_gray, R.drawable.color_gray));
        words.add(new Word("pink", "gulaabi", R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));*/

    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void populateLayout() {
        com.example.android.marwari.CustomAdapter customAdapter = new com.example.android.marwari.CustomAdapter(getContext(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                mAudio = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
                int result = mAudio.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), words.get(position).getAudioResource());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudio.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}
