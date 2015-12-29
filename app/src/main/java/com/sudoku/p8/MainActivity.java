package com.sudoku.p8;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private SudokuGame sudokuGame;
    private SudokuView SudokuView;
    private int difficulty;
    private String difficultyS;
    private Button[] numpad;
    private Button clear;
    private android.support.v7.app.ActionBar actionBar;
    private Chronometer chrono;
    private long lastPause;
    private boolean gameWon = false;
    public boolean resumeGame;
    public String savedGrille;
    private SudokuPrefs prefs;
    private boolean chronoStopped = false;
    private boolean printOnce = true;
    private boolean solved = false;
    private MenuItem resetItem, solveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        SudokuView = (SudokuView)findViewById(R.id.SudokuView);
        SudokuView.setVisibility(View.VISIBLE);

        prefs = new SudokuPrefs(this);

        resumeGame = getIntent().getBooleanExtra("resumeGame",false);

        if(resumeGame) {
            savedGrille = prefs.resumeSudoku();
            difficulty = prefs.getSavedDiff();
        }
        else difficulty = getIntent().getIntExtra("difficulty", 0);

        switch(difficulty) {
            case 2 : difficultyS=getString(R.string.easy_mode); break;
            case 3 : difficultyS=getString(R.string.medium_mode); break;
            case 5 : difficultyS=getString(R.string.hard_mode); break;
            default : break;
        }

        SudokuView.setDifficulty(difficulty);

        chrono = new Chronometer(this);
        chrono.setVisibility(View.GONE);
        actionBar.setCustomView(chrono);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        if(!resumeGame) chrono.setBase(SystemClock.elapsedRealtime());
        else restoreTimer();

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                chronometer.refreshDrawableState();
                updateTitle(chronometer.getText().toString());
            }
        });

        chrono.start();

        numpad = new Button[9];

        numpad[0] = (Button) findViewById(R.id.button1);
        numpad[1] = (Button) findViewById(R.id.button2);
        numpad[2] = (Button) findViewById(R.id.button3);
        numpad[3] = (Button) findViewById(R.id.button4);
        numpad[4] = (Button) findViewById(R.id.button5);
        numpad[5] = (Button) findViewById(R.id.button6);
        numpad[6] = (Button) findViewById(R.id.button7);
        numpad[7] = (Button) findViewById(R.id.button8);
        numpad[8] = (Button) findViewById(R.id.button9);

        clear = (Button) findViewById(R.id.buttonClear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SudokuView.clearCase();
            }
        });

        for(final Button button : numpad) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SudokuView.getNum(Integer.parseInt(button.getText().toString()));
                }
            });
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

        if(!(prefs.getSoundPreference()))
        {
            Music.getInstance().stopPlaying();
        }
        lastPause = SystemClock.elapsedRealtime();
        chrono.stop();
        chronoStopped = true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(chronoStopped && !gameWon) {
            chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - lastPause);
            chrono.start();
        }
    }

    public void sudokuSolvedDialog() {
        if (printOnce) {
            chrono.stop();
            gameWon = true;


            runOnUiThread(new Runnable() {
                public void run() {
                    prefs.saveScore(chrono.getText().toString(), gameWon, difficulty);
                    prefs.deletelvl(difficulty);

                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle(R.string.you_won);
                    alert.setMessage(getString(R.string.solved_time) + " " + chrono.getText().toString());
                    alert.setPositiveButton(getString(R.string.victory_great), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            });
            printOnce=false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        resetItem = menu.findItem(R.id.reload);
        solveItem = menu.findItem(R.id.validate_title);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onSupportNavigateUp() {
        backToMenu();

        return super.onSupportNavigateUp();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.reload:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.reload_confirm_title);
                alert.setMessage(getString(R.string.reload_confirm));
                alert.setPositiveButton(R.string.reload, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        SudokuView.reset();
                    }
                });

                alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
                alert.show();
                return true;

            case R.id.validate_title:
                AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
                alert2.setTitle(R.string.validate_title);
                alert2.setMessage(getString(R.string.validate_confirm));
                alert2.setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        SudokuView.solve();
                        chrono.stop();
                        solveItem.setEnabled(false);
                        resetItem.setEnabled(false);
                        prefs.deletelvl(difficulty);
                        solved = true;
                    }
                });

                alert2.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
                alert2.show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void updateTitle(String text) {
        setTitle(difficultyS + " - " + text);
    }

    public int getDifficulty() {

        return this.difficulty;
    }

    public void restoreTimer() {
        String score = prefs.getScore(difficulty);
        String[] time = score.split(":");
        int min = Integer.parseInt(time[0]);
        int sec = Integer.parseInt(time[1]);
        chrono.setBase(SystemClock.elapsedRealtime() - (min * 60000 + sec * 1000));
        chrono.setText(score);
    }

    public void backToMenu() {

        chrono.stop();

        switch(difficulty) {
            case 2 :
                prefs.saveSudoku(SudokuView.saveGrille(), "easy");
                if(!gameWon)
                    prefs.saveScore(chrono.getText().toString(), false, difficulty);
                break;
            case 3 :
                prefs.saveSudoku(SudokuView.saveGrille(), "medium");
                if(!gameWon)
                    prefs.saveScore(chrono.getText().toString(), false, difficulty);
                break;
            case 5 :
                prefs.saveSudoku(SudokuView.saveGrille(), "hard");
                if(!gameWon)
                    prefs.saveScore(chrono.getText().toString(), false, difficulty);
                break;
            default : break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!solved && !gameWon)
            backToMenu();
    }

    protected void onStart() {
        super.onStart();
        if (!(prefs.getSoundPreference())) {
            Music.getInstance().initalizeMediaPlayer(MainActivity.this, R.raw.one);
            Music.getInstance().startPlaying();

        }
    }


}
