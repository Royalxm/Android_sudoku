package com.sudoku.p8;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SudokuView SudokuView;
    private Button[] numpad;
    private Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SudokuView = (SudokuView)findViewById(R.id.SudokuView);
        SudokuView.setVisibility(View.VISIBLE);

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


}
