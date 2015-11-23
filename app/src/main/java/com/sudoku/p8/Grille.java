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

    public void restoreGrille(String grille, SudokuGame game) {
        String[] cells = grille.split(";");
        int[][] tempGrid = new int[9][9];
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
           cellule.setPos((int)index/9, index%9);
           game.tab[(int)index/9][index%9] = cellule.getValue();
//           Log.d("SPLIT/CELLVALUE", "value " + cellule.getValue() + " locked? :" + cellule.isLocked()
//                   + "pos x" + cellule.getPos()[0] + " posy" + cellule.getPos()[1]);

           index++;
       }

        game.fillSudoku(game.tab, 0, 0);

//        System.out.println("RESTOREGRILLE");
//            for (int i2 = 0; i2 != 9; i2++) {
//                for (int i3 = 0; i3 != 9; i3++) {
//                    System.out.printf("%2d", game.tab[i2][i3]);
//
//                    if (i3 % 3 == 2)
//                        System.out.printf("  ");
//                }
//                System.out.println();
//                if (i2 % 3 == 2) System.out.println();
//
//            }
//        System.out.println("FINRESTOREGRILLE");

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
