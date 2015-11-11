package com.sudoku.p8;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SudokuView SudokuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SudokuView = (SudokuView)findViewById(R.id.SudokuView);

        SudokuView.setVisibility(View.VISIBLE);
    }


}
