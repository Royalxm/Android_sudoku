package com.sudoku.p8;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends Activity {

    private TextView textViewEasy, textViewEasyBestscore, textViewEasyScore, textViewMedium,
            textViewMediumBestscore, textViewMediumScore, textViewHard, textViewHardBestscore,textViewHardScore;

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
    }
}
