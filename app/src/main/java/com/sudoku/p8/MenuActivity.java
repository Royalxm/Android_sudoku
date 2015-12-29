package com.sudoku.p8;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MenuActivity extends Activity {

    Button reprendre, easy, medium, hard, options, scores, about;
    SudokuPrefs prefs;
    private int currentLevel;
    private boolean jeuEnCours;
    private boolean newIntent = false;
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setLocale();

        setContentView(R.layout.activity_menu);

        reprendre = (Button) findViewById(R.id.buttonReprendre);
        easy = (Button) findViewById(R.id.buttonEasy);
        medium = (Button) findViewById(R.id.buttonMedium);
        hard = (Button) findViewById(R.id.buttonHard);
        options = (Button) findViewById(R.id.buttonOptions);
        scores = (Button) findViewById(R.id.buttonScore);
        about = (Button) findViewById(R.id.buttonAbout);



        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/century-gothic.ttf");

        //Jeu en cours
        jeuEnCours = prefs.canResume();

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);


        reprendre.setTypeface(font, Typeface.BOLD);
        easy.setTypeface(font, Typeface.BOLD);
        medium.setTypeface(font, Typeface.BOLD);
        hard.setTypeface(font, Typeface.BOLD);
        options.setTypeface(font, Typeface.BOLD);
        scores.setTypeface(font, Typeface.BOLD);
        about.setTypeface(font, Typeface.BOLD);

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, OptionsActivity.class);
                //   intent.putExtra("key",sound);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ScoresActivity.class);
                startActivity(intent);
            }
        });

        reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("resumeGame", jeuEnCours);
                startActivity(intent);
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.existsE()) {
                    jeuenCoursPopup(getString(R.string.easy_mode));
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 2);
                    startActivity(intent);
                }
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.existsM()) {
                    jeuenCoursPopup(getString(R.string.medium_mode));
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 3);
                    startActivity(intent);
                }
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.existsH()) {
                    jeuenCoursPopup(getString(R.string.hard_mode));
                }
                else {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 5);
                    startActivity(intent);
                }
            }
        });
    }

    private void setLocale() {
        Resources res = getBaseContext().getResources();

        prefs = new SudokuPrefs(this);

        if(prefs.getLocale()==null)
            prefs.setLocale(res.getConfiguration().locale.getLanguage());
        else {
            Locale locale;
            if(prefs.getLocale().equals("en")) {
                locale = new Locale("en", "us");
            }
            else locale = new Locale("fr");

            Locale.setDefault(locale);
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = locale;
            res.updateConfiguration(conf, dm);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!(prefs.getSoundPreference())) {
            Music.getInstance().initalizeMediaPlayer(MenuActivity.this, R.raw.one);
            Music.getInstance().startPlaying();
        }

        setLocale();

        Toast.makeText(this,"locale : "+getResources().getConfiguration().locale.getLanguage(), Toast.LENGTH_SHORT).show();


        Toast.makeText(this,getString(R.string.resume_game), Toast.LENGTH_SHORT).show();



        if(!newIntent) {
            jeuEnCours = prefs.canResume();

            if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
            else reprendre.setVisibility(View.GONE);

            Toast.makeText(this,"onstart", Toast.LENGTH_SHORT).show();
        }

        newIntent = false;

    }

    @Override
    protected void onPause() {
        super.onPause();
    if(!(prefs.getSoundPreference()))
        {
            Music.getInstance().stopPlaying();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //MainActivity.this.backToMenu();

        jeuEnCours = prefs.canResume();

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);

        newIntent = true;

        Toast.makeText(this,"onnewintent", Toast.LENGTH_SHORT).show();
    }

    private void jeuenCoursPopup(final String difficultyButton) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.puzzle_inprogress_title);
        alert.setMessage(getString(R.string.puzzle_inprogress, difficultyButton));
        alert.setPositiveButton(getString(R.string.resume_game), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                if (difficultyButton.equals(getString(R.string.easy_mode))) {
                    prefs.saveStringPreference(SudokuPrefs.RESUME_DIFF, "easy");
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("resumeGame", jeuEnCours);
                    startActivity(intent);
                }
                else if(difficultyButton.equals(getString(R.string.medium_mode))) {
                    prefs.saveStringPreference(SudokuPrefs.RESUME_DIFF, "medium");
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("resumeGame", jeuEnCours);
                    startActivity(intent);
                }
                else if(difficultyButton.equals(getString(R.string.hard_mode))) {
                    prefs.saveStringPreference(SudokuPrefs.RESUME_DIFF, "hard");
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("resumeGame", jeuEnCours);
                    startActivity(intent);
                }

            }
        });
        alert.setNegativeButton(getString(R.string.new_game), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (difficultyButton.equals(getString(R.string.easy_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 2);
                    startActivity(intent);
                }
                else if(difficultyButton.equals(getString(R.string.medium_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 3);
                    startActivity(intent);
                }
                else if(difficultyButton.equals(getString(R.string.hard_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 5);
                    startActivity(intent);
                }
            }
        });
        alert.show();
    }
}
