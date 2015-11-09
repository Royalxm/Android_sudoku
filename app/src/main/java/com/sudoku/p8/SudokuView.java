package com.sudoku.p8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
        linePaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw vertical lines
        for (int c = 0; c <= 9; c++) {
            float x = (c * cellWidth);
            canvas.drawLine(x, 0, x, height-50, linePaint);
        }

        // draw horizontal lines
        for (int r = 0; r <= 9; r++) {
            float y = r * cellHeight;
            canvas.drawLine(0, y, width, y, linePaint);
        }
        
        


    }
}
