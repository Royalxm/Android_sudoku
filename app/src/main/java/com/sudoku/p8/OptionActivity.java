package com.sudoku.p8;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class OptionActivity extends Activity {

    Button onmusic,offmusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option2);


        offmusic = (Button)findViewById(R.id.offmusic);
        onmusic = (Button)findViewById(R.id.onmusic);

        offmusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Music.getInstance().stopPlaying();
            }
        });

        onmusic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(Music.getInstance().playe() == 0) {
                    Music.getInstance().initalizeMediaPlayer(OptionActivity.this, R.raw.one);
                    Music.getInstance().startPlaying();
                }
            }
        });
    }


}
