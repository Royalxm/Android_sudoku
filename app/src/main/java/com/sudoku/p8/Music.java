package com.sudoku.p8;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Royal on 25/12/15.
 */
public class Music {

    MediaPlayer sound;

    private static Music refrence = null;

    public static Music getInstance(){
        if(refrence == null){
            refrence = new Music ();
        }
        return refrence;
    }

    public void initalizeMediaPlayer(Context context, int musicId){

            sound = MediaPlayer.create(context, musicId);

    }

    public int playe() {
        if (sound.isPlaying()) {
           return 1;
        }
        else
            return 0;
    }

    public void startPlaying(){
        if (!(sound.isPlaying())) {
            sound.start();
            sound.setLooping(true);
        }

    }

    public void stopPlaying(){
        if ((sound.isPlaying())) {
            sound.stop();
        }
    }
}
