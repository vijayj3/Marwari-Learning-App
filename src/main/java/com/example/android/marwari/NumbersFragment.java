package com.example.android.marwari;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

    private ArrayList<Word> words;
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudio;
    View rootview;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.word_list, container, false);

        wordsPopulate();
        populateLayout();

        return rootview;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("VJ","Fragment was created");
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    public void wordsPopulate() {
        words = new ArrayList<Word>();

        words.add(new Word("one", "ek", R.raw.number_one, R.drawable.number_one));
        words.add(new Word("two", "be", R.raw.number_two, R.drawable.number_two));
        words.add(new Word("three", "teen", R.raw.number_three, R.drawable.number_three));
        words.add(new Word("four", "syar", R.raw.number_four, R.drawable.number_four));
        words.add(new Word("five", "pos", R.raw.number_five, R.drawable.number_five));
        words.add(new Word("six", "sa", R.raw.number_six, R.drawable.number_six));
        words.add(new Word("seven", "haath", R.raw.number_seven, R.drawable.number_seven));
        words.add(new Word("eight", "aat", R.raw.number_eight, R.drawable.number_eight));
        words.add(new Word("nine", "nav", R.raw.number_nine, R.drawable.number_nine));
        words.add(new Word("ten", "das", R.raw.number_ten, R.drawable.number_ten));
    }

    public void populateLayout() {
        com.example.android.marwari.CustomAdapter customAdapter = new com.example.android.marwari.CustomAdapter(getContext(), words, R.color.category_numbers);
        ListView listView = (ListView) rootview.findViewById(R.id.list);
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
