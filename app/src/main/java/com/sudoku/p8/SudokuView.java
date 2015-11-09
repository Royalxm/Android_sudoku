package com.sudoku.p8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * Created by cap-one on 09/11/15.
 */
public class SudokuView extends SurfaceView  implements SurfaceHolder.Callback, Runnable {

    private Paint linePaint;
    private boolean in = true;
    private int width, height, cellWidth, cellHeight;
    private SurfaceHolder holder;
    private  Thread  cv_thread;

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        width = getWidth();
        height = getHeight();
        cellWidth = cellHeight = 20;

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);

        cv_thread   = new Thread(this);

        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");

        }
//        }
//        else
//        {
//
//            Toast.makeText(getContext(),"fjenf", Toast.LENGTH_SHORT).show();
//
//        }



    }


    private void paintGrid(Canvas canvas) {
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
    }


    private void nDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);
        //Toast.makeText(getContext(),"fjenf", Toast.LENGTH_SHORT).show();

        paintGrid(canvas);
    }

    public void run() {
        Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(40);
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch(Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN : " + e.toString());
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
