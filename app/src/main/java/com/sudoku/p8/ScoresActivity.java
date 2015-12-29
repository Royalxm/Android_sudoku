package com.sudoku.p8;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ScoresActivity extends Activity {

    private TextView textViewEasy, textViewEasyBestscore, textViewEasyScore, textViewMedium,
            textViewMediumBestscore, textViewMediumScore, textViewHard, textViewHardBestscore,textViewHardScore;

    private SudokuPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/century-gothic.ttf");

        textViewEasy = (TextView) findViewById(R.id.textViewEasy);
        textViewMedium = (TextView) findViewById(R.id.textViewMedium);
        textViewHard = (TextView) findViewById(R.id.textViewHard);

        textViewEasyBestscore = (TextView) findViewById(R.id.textViewEasyBestscore);
        textViewMediumBestscore = (TextView) findViewById(R.id.textViewMediumBestscore);
        textViewHardBestscore = (TextView) findViewById(R.id.textViewHardBestscore);

        textViewEasyScore = (TextView) findViewById(R.id.textViewEasyScore);
        textViewMediumScore = (TextView) findViewById(R.id.textViewMediumScore);
        textViewHardScore = (TextView) findViewById(R.id.textViewHardScore);

        textViewEasy.setTypeface(font, Typeface.BOLD);
        textViewMedium.setTypeface(font, Typeface.BOLD);
        textViewHard.setTypeface(font, Typeface.BOLD);

        textViewEasyBestscore.setTypeface(font, Typeface.BOLD);
        textViewMediumBestscore.setTypeface(font, Typeface.BOLD);
        textViewHardBestscore.setTypeface(font, Typeface.BOLD);

        textViewEasyScore.setTypeface(font, Typeface.BOLD);
        textViewMediumScore.setTypeface(font, Typeface.BOLD);
        textViewHardScore.setTypeface(font, Typeface.BOLD);

        prefs = new SudokuPrefs(this);

        textViewEasyBestscore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.BESTSCORE_EASY)));
        textViewMediumBestscore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.BESTSCORE_MED)));
        textViewHardBestscore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.BESTSCORE_HARD)));

        textViewEasyScore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.CURRSCORE_EASY)));
        textViewMediumScore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.CURRSCORE_MED)));
        textViewHardScore.append(" "+formatString(prefs.getStringPreference(SudokuPrefs.CURRSCORE_HARD)));
    }

    private String formatString(String s) {
        if(s==null) return "";
        return s;
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
    protected void onStart() {
        super.onStart();

        if (!(prefs.getSoundPreference())) {
            Music.getInstance().initalizeMediaPlayer(ScoresActivity.this, R.raw.one);
            Music.getInstance().startPlaying();

        }
    }
}
