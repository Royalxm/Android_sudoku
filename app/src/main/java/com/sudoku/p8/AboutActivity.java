package com.sudoku.p8;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {
    SudokuPrefs prefs;

    TextView aboutTextView1,aboutTextView2,aboutTextView3,aboutTextView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        prefs = new SudokuPrefs(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/century-gothic.ttf");

        aboutTextView1 = (TextView) findViewById(R.id.aboutTextView1);
        aboutTextView2 = (TextView) findViewById(R.id.aboutTextView2);
        aboutTextView3 = (TextView) findViewById(R.id.aboutTextView3);
        aboutTextView4 = (TextView) findViewById(R.id.aboutTextView4);


        aboutTextView1.setTypeface(font, Typeface.BOLD);
        aboutTextView2.setTypeface(font, Typeface.BOLD);
        aboutTextView3.setTypeface(font, Typeface.BOLD);
        aboutTextView4.setTypeface(font, Typeface.BOLD);

    }


    @Override
    protected void onStart() {

        super.onStart();
        if (!(prefs.getSoundPreference())) {
            Music.getInstance().initalizeMediaPlayer(AboutActivity.this, R.raw.one);
            Music.getInstance().startPlaying();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(!(prefs.getSoundPreference()))
        {
            Music.getInstance().stopPlaying();
        }

    }
}
