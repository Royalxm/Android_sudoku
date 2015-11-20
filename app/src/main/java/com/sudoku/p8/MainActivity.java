package com.sudoku.p8;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        actionBar.setDisplayShowCustomEnabled(true);

       if(!resumeGame) chrono.setBase(SystemClock.elapsedRealtime());

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

        lastPause = SystemClock.elapsedRealtime();
        chrono.stop();
        chronoStopped = true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(chronoStopped) {
            chrono.setBase(chrono.getBase() + SystemClock.elapsedRealtime() - lastPause);
            chrono.start();
        }
    }

    public void sudokuSolvedDialog() {
            chrono.stop();
            gameWon = true;

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.you_won);
            alert.setMessage(getString(R.string.solved_time)+chrono.getText().toString());
            alert.setPositiveButton("Super !", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reload:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.you_won);
                alert.setMessage(getString(R.string.solved_time) + " "+chrono.getText().toString());
                alert.setPositiveButton("Super !", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateTitle(String text) {
        setTitle(difficultyS+" - "+text);
    }

    public int getDifficulty() {

        return this.difficulty;
    }

    public void restoreTimer() {
        Log.d("restored time", "on restored time");
        String[] cells = savedGrille.split(";");
        for(String cellData : cells) {
            String[] cell = cellData.split("=");

            if(cell[1].equals("time")) {
                String[] time = cell[0].split(":");
                int min = Integer.parseInt(time[0]);
                int sec = Integer.parseInt(time[1]);
                chrono.setBase(SystemClock.elapsedRealtime() - (min * 60000 + sec * 1000));
                chrono.setText(cell[0]);
                Log.d("restored time", "restored time : " +cell[0]+" ("+(SystemClock.elapsedRealtime() - (min * 60000 + sec * 1000))+")");
                savedGrille = savedGrille.substring(0, savedGrille.length()-cellData.length()-1);
                //return;
            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        chrono.stop();

        if(!gameWon) {
            switch(difficulty) {
                case 2 :  prefs.saveSudoku(SudokuView.saveGrille(chrono.getText().toString()), "easy");
                    break;
                case 3 :
                    prefs.saveSudoku(SudokuView.saveGrille(chrono.getText().toString()), "medium");
                    break;
                case 5 :
                    prefs.saveSudoku(SudokuView.saveGrille(chrono.getText().toString()), "hard");
                    break;
                default : break;
            }
        }

       // Toast.makeText(this, "Sudoku sauvegardé", Toast.LENGTH_SHORT).show();
    }
}
