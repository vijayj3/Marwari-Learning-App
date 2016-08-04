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


public class PhrasesFragment extends Fragment {

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

        words.add(new Word("Where are you going?", "hen jaaye?", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tharu nom ka hai", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "maaru nom..", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "kikan hai?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good", "thik hu", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "thu aavi ke?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes,I'm coming", "haan, mu aavu", R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming", "mu aaveriyo hu", R.raw.phrase_im_coming));
        words.add(new Word("Let's go", "haalo", R.raw.phrase_lets_go));
        words.add(new Word("Come here", "ate aav", R.raw.phrase_come_here));

    }

    public void populateLayout() {
        com.example.android.marwari.CustomAdapter customAdapter = new com.example.android.marwari.CustomAdapter(getContext(), words, R.color.category_phrases);
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
