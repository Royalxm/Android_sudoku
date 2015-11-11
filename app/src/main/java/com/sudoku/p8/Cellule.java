package com.sudoku.p8;

import android.graphics.Rect;

/**
 * Created by Royal on 11/11/15.
 */
public class Cellule {

    private float x,y;
    private Rect rect;
    private boolean selected = false;

        public Cellule(Rect rect)
        {
            this.x = x;
            this.y = y;
            this.rect = rect;

        }

    public Rect getRect() {return this.rect; }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setSelected() { this.selected=true; }

}
