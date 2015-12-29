package com.sudoku.p8;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;


public class OptionsActivity extends Activity {

    SudokuPrefs pref;
    Button onmusic, offmusic, langfr, langen;
    private SudokuPrefs prefs;
    private Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        offmusic = (Button) findViewById(R.id.offmusic);
        onmusic = (Button) findViewById(R.id.onmusic);
        langfr = (Button) findViewById(R.id.button12);
        langen = (Button) findViewById(R.id.button11);

        prefs = new SudokuPrefs(this);

        offmusic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Music.getInstance().stopPlaying();
                prefs.saveSoundPreference(true);
            }
        });

        onmusic.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (prefs.getSoundPreference()) {
                    Music.getInstance().initalizeMediaPlayer(OptionsActivity.this, R.raw.one);
                    Music.getInstance().startPlaying();
                    prefs.saveSoundPreference(false);
                }
            }
        });


        langfr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "ffr", Toast.LENGTH_SHORT).show();
                Locale locale = new Locale("fr");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);
                prefs.setLocale("fr");

                recreate();
            }
        });

        langen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "efn", Toast.LENGTH_SHORT).show();
                Locale locale = new Locale("en", "us");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = locale;
                res.updateConfiguration(conf, dm);
                prefs.setLocale("en");
                recreate();
            }
        });

    }
    protected void onStart() {
        super.onStart();
        if (!(prefs.getSoundPreference())) {
            Music.getInstance().initalizeMediaPlayer(OptionsActivity.this, R.raw.one);
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
