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

import java.util.ArrayList;


public class FamilyFragment extends Fragment {

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

    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.word_list,container,false);

        wordsPopulate();
        populateLayout();

        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


    public void wordsPopulate() {
        words = new ArrayList<Word>();

        words.add(new Word("father", "papa", R.raw.family_father, R.drawable.family_father));
        words.add(new Word("mother", "ma", R.raw.family_mother, R.drawable.family_mother));
        words.add(new Word("son", "beta", R.raw.family_son, R.drawable.family_son));
        words.add(new Word("daughter", "beti", R.raw.family_daughter, R.drawable.family_daughter));
        words.add(new Word("older brother", "bhaiya", R.raw.family_older_brother, R.drawable.family_older_brother));
        words.add(new Word("younger brother", "bhai", R.raw.family_younger_brother, R.drawable.family_younger_brother));
        words.add(new Word("older sister", "jiji", R.raw.family_older_sister, R.drawable.family_older_sister));
        words.add(new Word("younger sister", "ban", R.raw.family_younger_sister, R.drawable.family_younger_sister));
        words.add(new Word("grandmother", "bai", R.raw.family_grandmother, R.drawable.family_grandmother));
        words.add(new Word("grandfather", "jisa", R.raw.family_grandfather, R.drawable.family_grandfather));

    }

    public void populateLayout() {
        com.example.android.marwari.CustomAdapter customAdapter = new com.example.android.marwari.CustomAdapter(getContext(), words, R.color.category_family);
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
