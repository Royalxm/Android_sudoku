package com.sudoku.p8;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

    Button reprendre, easy, medium, hard, options, about;
    SudokuPrefs prefs;
    private int currentLevel;
    private boolean jeuEnCours;
    MediaPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //music
     //   sound = MediaPlayer.create(this,R.raw.one);
      //  sound.start();
     //   sound.setLooping(true);
     /*   Music muc = new Music();
         muc.start(this);*/
        Music.getInstance().initalizeMediaPlayer(this, R.raw.one);
        Music.getInstance().startPlaying();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        reprendre = (Button) findViewById(R.id.buttonReprendre);
        easy = (Button) findViewById(R.id.buttonEasy);
        medium = (Button) findViewById(R.id.buttonMedium);
        hard = (Button) findViewById(R.id.buttonHard);
        options = (Button) findViewById(R.id.buttonOptions);
        about = (Button) findViewById(R.id.buttonAbout);

        prefs = new SudokuPrefs(this);



        //Jeu en cours
        jeuEnCours = prefs.canResume();

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, OptionActivity.class);
             //   intent.putExtra("key",sound);
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
                if(jeuEnCours) {
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
                if(jeuEnCours) {
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
                if(jeuEnCours) {
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

    @Override
    protected void onStart() {
        super.onStart();

         //Jeu en cours
        jeuEnCours = prefs.canResume();

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);
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
