package com.sudoku.p8;

import android.graphics.Rect;

/**
 * Created by Royal on 11/11/15.
 */
public class Cellule {

    private int value;
    private Rect rect;
    private boolean selected = false;
    private boolean isLocked = false;

        public Cellule(Rect rect)
        {
            this.rect = rect;
            this.value = 0;
        }

    public void setValue(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public Rect getRect() {return this.rect; }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setSelected() { this.selected=true; }

}
