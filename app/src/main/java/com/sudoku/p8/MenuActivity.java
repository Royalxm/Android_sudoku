package com.sudoku.p8;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends Activity {

    Button reprendre, easy, medium, hard, options, about;
    SudokuPrefs prefs;
    private int currentLevel;
    private boolean jeuEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        jeuEnCours = prefs.getBooleanPreference("jeuEnCours");

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);


        reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("resumeGame", true);
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

        Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();

        //Jeu en cours
        jeuEnCours = prefs.getBooleanPreference("jeuEnCours");

        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
        else reprendre.setVisibility(View.GONE);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        Toast.makeText(this, "newInt", Toast.LENGTH_SHORT).show();
//
//        //Jeu en cours
//        jeuEnCours = prefs.getBooleanPreference("jeuEnCours");
//
//        if(jeuEnCours) reprendre.setVisibility(View.VISIBLE);
//        else reprendre.setVisibility(View.GONE);
//
//    }

    private void jeuenCoursPopup(final String difficulty) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.puzzle_inprogress_title);
        alert.setMessage(getString(R.string.puzzle_inprogress, difficulty));
        alert.setPositiveButton(getString(R.string.resume_game), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                Log.d("SAVEDGRILLE", prefs.getStringPreference("grilleJeuEnCours"));
            }
        });
        alert.setNegativeButton(getString(R.string.new_game), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if (difficulty.equals(getString(R.string.easy_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 2);
                    startActivity(intent);
                }
                else if(difficulty.equals(getString(R.string.medium_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 3);
                    startActivity(intent);
                }
                else if(difficulty.equals(getString(R.string.hard_mode))) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    intent.putExtra("difficulty", 5);
                    startActivity(intent);
                }
            }
        });
        alert.show();
    }
}
