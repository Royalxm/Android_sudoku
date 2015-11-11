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
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by cap-one on 09/11/15.
 */
public class SudokuView extends SurfaceView  implements SurfaceHolder.Callback, Runnable {

    private Paint linePaint;
    private boolean in = true;
    private int width, height, cellWidth, cellHeight;
    SurfaceHolder holder;
    private  Thread  cv_thread;
    private Grille grille;

    public SudokuView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        setLayoutParams(new LinearLayout.LayoutParams(this.getWidth(), this.getWidth()));
        grille  = new Grille();


        init();



        cv_thread   = new Thread(this);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.d("MES b4", "width: " + getMeasuredWidth() + " height: " + getMeasuredHeight());

        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        Log.d("MES after", "width: " + getMeasuredWidth() + " height: " + getMeasuredHeight());
    }

    private void init() {

        width = this.getWidth();
        height = this.getHeight();
        cellWidth = width / 9;
        cellHeight = height/9;

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.FILL);

        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.d("-FCT-", "cv_thread.start()");

        }
    }


    private void paintGrid(Canvas canvas) {
        // draw vertical lines

        int r = 0;

        for (int c = 0; c <= 9; c++,r++) {
            float x = (float)(c * cellWidth);
            float y = (float) (r * cellHeight);
            if(c % 3==0) linePaint.setStrokeWidth(7);
            else
                linePaint.setStrokeWidth(2);
            canvas.drawLine(x, 0, x, height, linePaint);
            // draw horizontal lines
            if(r % 3==0) linePaint.setStrokeWidth(7);
            else
                linePaint.setStrokeWidth(2);
            canvas.drawLine(0, y, width, y, linePaint);



        }

        for(int c = 0; c <= 9; c++)
        {
            float y = (float) (c * cellHeight)- cellHeight/2;
            for( r = 0; r <= 9;r++)
            {
                float x = (float)(r * cellWidth) + cellWidth/2;
                Paint paint = new Paint();

                paint.setColor(Color.BLACK);
                paint.setTextSize(25);
                canvas.drawText("0", x, y, paint);

                grille.add(new Cellule(x,y));
            }
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
                Log.e("-> RUN <-", "PB DANS RUN : "+e.toString());
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        init();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }
}
