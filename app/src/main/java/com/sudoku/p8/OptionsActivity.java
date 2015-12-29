package com.sudoku.p8;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class OptionsActivity extends Activity {

    Button onmusic,offmusic, langfr, langen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        offmusic = (Button)findViewById(R.id.offmusic);
        onmusic = (Button)findViewById(R.id.onmusic);
        langfr = (Button)findViewById(R.id.button12);
        langen = (Button)findViewById(R.id.button11);

        offmusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Music.getInstance().stopPlaying();
            }
        });

        onmusic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(Music.getInstance().playe() == 0) {
                    Music.getInstance().initalizeMediaPlayer(OptionsActivity.this, R.raw.one);
                    Music.getInstance().startPlaying();
                }
            }
        });



    }


}
