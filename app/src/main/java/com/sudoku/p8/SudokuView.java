package com.sudoku.p8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


/**
 * Created by cap-one on 09/11/15.
 */
public class SudokuView extends SurfaceView  implements SurfaceHolder.Callback, Runnable {

    private Paint linePaint, cellBorderPaint;
    private boolean in = true;
    private int width, height, cellWidth, cellHeight;
    SurfaceHolder holder;
    private  Thread  cv_thread;
    private Grille grille;
    private Cellule paintedCell;
    private SudokuGame game;
    private boolean  fp = false;
    private int difficulty;
    private MainActivity activity;
    private boolean paintCell = false;

    public SudokuView(Context context, AttributeSet attrs) {

        super(context, attrs);

        activity = (MainActivity)context;

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);
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


        grille  = new Grille(width, height);

        game = new SudokuGame();

        grille.initGrid();

        //if(!activity.resumeGame)
        game.init(grille, difficulty);
        //else
        //
        if(activity.resumeGame) {
            grille.restoreGrille(activity.savedGrille, game);
        }


       // game.printGrille(grille);

        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);

        cellBorderPaint = new Paint();
        cellBorderPaint.setColor(Color.RED);
        cellBorderPaint.setStyle(Paint.Style.STROKE);
        cellBorderPaint.setStrokeWidth(7);

        if ((cv_thread!=null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.d("-FCT-", "cv_thread.start()");
        }
    }


    private void paintGrid(Canvas canvas) {
        // draw vertical lines

        float cellWidth = grille.getCellWidth();
        float cellHeight = grille.getCellHeight();

        for (int c = 1, r = 1; c < 9; c++,r++) {
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

    }

    private float getDimensions(int value, String type) {
        if(type.equals("dp"))
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, getResources().getDisplayMetrics());
        else if(type.equals("px"))
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,value, getResources().getDisplayMetrics());
     return 0;
    }



    private void paintValues(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(getDimensions(25, "dp"));
        Rect bounds =  new Rect();
        int i=0;
        for(Cellule cell : grille.getCellTab()) {
           // Log.d("Cell Value", "Cell n°+"+i+" : "+cell.getValue());
            paint.getTextBounds(String.valueOf(cell.getValue()), 0, 1, bounds);


            paint.setColor(Color.BLACK);

            if(cell.getValue() != 0) {
                if(!cell.isLocked())
                    paint.setColor(Color.BLUE);
                if(!cell.isLocked() && cell.isWrong(game, grille))
                    paint.setColor(Color.RED);
                canvas.drawText(String.valueOf(cell.getValue()), cell.getRect().exactCenterX(),
                    (cell.getRect().bottom - (bounds.height() / 2)) - getDimensions(5, "px"), paint);

            }
            i++;
        }
    }


    private void paintCell(Canvas canvas) {
        if(paintCell) {
            canvas.drawRect(paintedCell.getRect(), cellBorderPaint);
        }
    }


    private void nDraw(Canvas canvas) {
        canvas.drawRGB(255, 255, 255);
        if(game.isWon(grille)) {
            activity.sudokuSolvedDialog();
        }

        paintGrid(canvas);
        paintValues(canvas);
        paintCell(canvas);
    }


    public void getNum(int value) {
        if(grille != null) {
            Cellule selectedCell = grille.getSelectedCell();
            if(selectedCell != null) {
                if(!selectedCell.isLocked()){
                    grille.getSelectedCell().setValue(value);
                    Log.d("setval", "setted value : "+grille.getSelectedCell().getValue());
                }
            }
            else Toast.makeText(getContext(), R.string.select_a_case , Toast.LENGTH_LONG).show();
        }
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void clearCase() {
        if(grille != null) {
            Cellule cell = grille.getSelectedCell();
            if(cell != null)
                if(!cell.isLocked()) cell.setValue(0);
        }

    }


    public String saveGrille() {
        return grille.toString();
    }

    public void reset() {
        game.resetGrille(grille);
    }

    public void solve() {
        game.solveGrille(grille);
    }

    public void run() {
        Canvas c = null;
        while (in) {
          //  Log.i("-> FCT <-", "in");
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
                //Log.e("-> RUN <-", "PB DANS RUN : " + e.toString());
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged");
        if(!fp) {
            init();
            fp = true;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("-> FCT <-", "onTouchEvent X: "+ event.getX()+" Y: "+ event.getY());

       paintedCell = grille.getCell((int)event.getX(), (int)event.getY());

        if(paintedCell != null) {
            paintCell = true;
            Log.d("PAINT", "paint cell n°"+grille.getCellTab().indexOf(paintedCell));
            paintedCell.setSelected();
            grille.cellSelected(paintedCell);
        }

        return super.onTouchEvent(event);
    }
}
