package com.sudoku.p8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by cap-one on 09/11/15.
 */
public class SudokuView extends SurfaceView {

    private Paint linePaint;

    private int width, height, cellWidth, cellHeight;

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        width = getWidth();
        height = getHeight();
        cellWidth = cellHeight = 20;

        linePaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw vertical lines
        for (int c = 0; c <= 9; c++) {
            float x = (c * cellWidth);
            canvas.drawLine(x, 0, x, height, linePaint);
        }

        // draw horizontal lines
        for (int r = 0; r <= 9; r++) {
            float y = r * cellHeight;
            canvas.drawLine(0, y, width, y, linePaint);
        }
        
        
        private boolean checkSudokuStatus(int[][] grid) {
    for (int i = 0; i < 9; i++) {

        int[] row = new int[9];
        int[] square = new int[9];
        int[] column = grid[i].clone();

        for (int j = 0; j < 9; j ++) {
            row[j] = grid[j][i];
            square[j] = grid[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3];
        }
        if (!(validate(column) && validate(row) && validate(square)))
            return false;
    }
    return true;
}

private boolean validate(int[] check) {
    int i = 0;
    Arrays.sort(check);
    for (int number : check) {
        if (number != ++i)
            return false;
    }
    return true;
}

/*
// cleaner 
for (k=0; k < difficulty; ) {
 
  save1 = solution[i][j];
  solution[i][j] = 0;
  save2 = solution[8-i][8-j];
  solution[8-i][8-j] = 0;

  if (!can_be_solved(solution)) {
    // (i, j) was not a good choice!
    solution[i][j] = save1;
    solution[8-i][8-j] = save2;
  }
  else {
    // it's still OK, let's go one step further
    k += 1;
  }
}

*/

    }
}
