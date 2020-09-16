package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.miwok.R.color.category_phrases;

public class phrases extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;// obect of audiomanager type
    private  AudioManager.OnAudioFocusChangeListener  mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }  else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {

        @Override

        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);// initialised
        final ArrayList<Word> phrases= new ArrayList<Word>();
        phrases.add(new Word("where are you going?","minto wuksus ",R.raw.phrase_where_are_you_going));
        phrases.add(new Word("what is your name?","tina oyaase",R.raw.phrase_what_is_your_name));
        phrases.add(new Word("my name is","oyaaset",R.raw.phrase_my_name_is));
        phrases.add(new Word("how are you feeling?","michakses",R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("i m feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        phrases.add(new Word("are you coming?","asaanas",R.raw.phrase_are_you_coming));
        phrases.add(new Word("yes i m coming","haae naaem",R.raw.phrase_yes_im_coming));
        phrases.add(new Word("lets go","aanem",R.raw.phrase_lets_go));
        phrases.add(new Word("come here","yuwutis",R.raw.phrase_come_here));
        phrases.add(new Word("where are you going?","anni'nem",R.raw.phrase_where_are_you_going));
        WordAdapter adapter=new WordAdapter(this, phrases,R.color.category_phrases);
        ListView l3=(ListView) findViewById(R.id.phrases_list);
        l3.setAdapter(adapter);

        l3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word mword=phrases.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(  mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    mediaPlayer = MediaPlayer.create(phrases.this, mword.getAudioid());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    private void releaseMediaPlayer() {

        // If the media player is not null, then it may be currently playing a sound.

        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
