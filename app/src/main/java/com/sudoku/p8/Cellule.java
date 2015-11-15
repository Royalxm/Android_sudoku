package com.sudoku.p8;

import android.graphics.Rect;

/**
 * Created by Royal on 11/11/15.
 */
public class Cellule {

    private int value;
    private int[] pos;
    private Rect rect;
    private boolean selected = false;
    private boolean isLocked = false;

        public Cellule(Rect rect)
        {
            this.rect = rect;
            this.value = 0;
            this.pos = new int[2];
        }

    public void setValue(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

    public void setPos(int x, int y) {
        this.pos[0] = x;
        this.pos[1] = y;
    }

    public int[] getPos() {
        return this.pos;
    }

    public Rect getRect() {return this.rect; }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setSelected() { this.selected=true; }

    public boolean isWrong(SudokuGame game, Grille grille) {
        return !game.checkSudoku2(grille, this.pos, this.value);
    }

}
