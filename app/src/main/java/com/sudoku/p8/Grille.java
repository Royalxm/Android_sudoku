package com.sudoku.p8;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by cap-one on 10/11/15.
 */
public class Grille {

   private int width, height, cellWidth, cellHeight;
    private ArrayList<Cellule> celltab;


    public Grille (int width, int height) {
        this.width = width;
        this.height = height;
        cellWidth = this.width / 9;
        cellHeight = this.height/9;
        celltab = new ArrayList<>();
    }

    public void initGrid() {
//        int r = 1;
//
//        for (int c = 1; c <= 9; c++,r++) {
//            float x = (float)(c * cellWidth);
//            float y = (float) (r * cellHeight);
//
//            Rect rect = new Rect((int) x, (int)y, (int)x+cellWidth, (int)y+cellHeight);
//
//            this.add(new Cellule((rect)));
//        }


        for(int c = 0; c <= 9; c++)
        {
            float x = (float)(c * cellWidth);

            for(int  r = 0; r <= 9;r++)
            {
                float y = (float) (r * cellHeight);

                Rect rect = new Rect((int) x, (int)y, (int)x+cellWidth, (int)y+cellHeight);

                this.add(new Cellule((rect)));


            }
        }
    }

    public float getCellWidth() { return this.cellWidth; }

    public float getCellHeight() { return this.cellHeight; }

    public void add (Cellule c) {
        celltab.add(c);
    }

    public ArrayList<Cellule> getCellTab() {
        return celltab;
    }

    public Cellule getCell(int x, int y) {
        for(Cellule cell : celltab) {
            if(cell.getRect().contains(x,y)) return cell;
        }
        return null;
    }
}
