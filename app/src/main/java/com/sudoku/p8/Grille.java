package com.sudoku.p8;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cap-one on 10/11/15.
 */
public class Grille {

   private int width, height, cellWidth, cellHeight;
    private ArrayList<Cellule> celltab;
    private Cellule selectedCell;


    public Grille (int width, int height) {
        this.width = width;
        this.height = height;
        cellWidth = this.width / 9;
        cellHeight = this.height/9;
        celltab = new ArrayList<>();
        selectedCell = null;
    }

    public void initGrid() {

        int index = 0;

        for(int c = 0; c < 9; c++)
        {
            for(int  r = 0; r < 9;r++)
            {
                float x = (float)(c * cellWidth);
                float y = (float) (r * cellHeight);

                Rect rect = new Rect((int) y, (int)x, (int)y+cellWidth, (int)x+cellHeight);

                celltab.add(new Cellule((rect)));
            }
        }
    }

    public float getCellWidth() { return this.cellWidth; }

    public float getCellHeight() { return this.cellHeight; }

    public ArrayList<Cellule> getCellTab() {
        return celltab;
    }

    public Cellule getCell(int x, int y) {
        for(Cellule cell : celltab) {
            if(cell.getRect().contains(x,y)) return cell;
        }
        return null;
    }


    public void cellSelected(Cellule cell) {
        this.selectedCell = cell;
    }

    public Cellule getSelectedCell() {
        return this.selectedCell;
    }

    public void restoreGrille(String grille) {
        String[] cells = grille.split(";");
        int index = 0;
        int i, j = 0;

       for(String cellData : cells) {
           Log.d("SPLIT", cellData);

           String[] cell = cellData.split("=");
            Cellule cellule =  celltab.get(index);

            cellule.setValue(Integer.parseInt(cell[0]));
           cellule.setLocked(false);

           if(cell[1].equals("locked"))
               cellule.setLocked(true);
           cellule.setPos(index%9, (int)index/9);
           Log.d("SPLIT/CELLVALUE", "value "+cellule.getValue()+" locked? :"+cellule.isLocked()
                   +"pos x"+cellule.getPos()[0]+" posy"+cellule.getPos()[1]);

           index++;
       }

    }

    public String toString(String time) {
        StringBuilder sb = new StringBuilder();
        for(Cellule cell : celltab) {
            if(cell.isLocked()) {
                sb.append(cell.getValue()).append("=locked;");
            }
            if(cell.getValue()==0) sb.append(cell.getValue()).append("=vide;");
            if(!cell.isLocked() && cell.getValue()!=0) sb.append(cell.getValue()).append("=userval;");
        }
        sb.append(time).append("=time;");
        return sb.toString();
    }
}
