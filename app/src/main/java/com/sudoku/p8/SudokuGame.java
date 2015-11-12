package com.sudoku.p8;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by cap-one on 09/11/15.
 */
public class SudokuGame {

    final static int no = 9;
    static ArrayList cols[];
    static ArrayList rows[];
    static ArrayList boxes[];
    static int board[] = new int[1+(no*no)];
    static int[][] tab = new int[9][9];

    public SudokuGame() {

    }

    public void init (Grille grille) {
        int[][] tab2 = new int[9][9];
        int[][] tab3 = new int[9][9];
        int save1 = 0;
        int save2 = 0;

        resetBoard();
        generate(1);

        int i2=0;
        int i3=0;
        int i4=1;

        for (i2=0;i2!=9;i2++) {
            for (i3=0;i3!=9;i3++,i4++) {
                tab[i2][i3] = board[i4];
            }
        }
        int k; // difficult
        Random randomGenerator = new Random();

        i2=0;
        i3=0;
        i4=1;
        for (i2=0;i2!=9;i2++) {
            for (i3=0;i3!=9;i3++) {
                tab2[i2][i3] = tab[i2][i3];
            }
        }




        for (k=0; k != 10; ) {
            int i = randomGenerator.nextInt(4);
            int j = randomGenerator.nextInt(4);
            save1 = tab2[i][j];
            tab2[i][j] = 0;
            save2 = tab2[8-i][8-j];
            tab2[8-i][8-j] = 0;

            for (i2=0;i2!=9;i2++) {
                for (i3=0;i3!=9;i3++) {
                    tab3[i2][i3] = tab2[i2][i3];
                }
            }

            if (!fillSudoku(tab3, 0, 0)) {
                // (i, j) was not a good choice!
                tab2[i][j] = save1;
                tab2[8-i][8-j] = save2;

            }
            else {

                // it's still OK, let's go one step further
                k += 1;
            }

        }

        System.out.println();

        for (i2=0;i2!=9;i2++) {
            for (i3=0;i3!=9;i3++) {
                System.out.printf("%2d",tab3[i2][i3]);

                if(i3%3 == 2)
                    System.out.printf("  ");
            }
            System.out.println();
            if(i2%3 == 2) System.out.println();

        }


        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j ++) {
                grille.getCellTab().get(index++).setValue(tab2[i][j]);
               // Log.d("Value", "i:"+i+" j: "+j+" value: " + tab2[i][j]);
            }
        }

    }

    private int getBox(int row,int col){
        if(row <=3){
            if(col <=3){
                return 1;
            }else if(col <=6){
                return 2;
            }else{
                return 3;
            }
        }else if(row <=6){
            if(col <=3){
                return 4;
            }else if(col <=6){
                return 5;
            }else{
                return 6;
            }
        }else if(row <= 9){
            if(col <=3){
                return 7;
            }else if(col <=6){
                return 8;
            }else{
                return 9;
            }
        }
        return 0;
    }

    private boolean generate(int n){
        ArrayList<Integer> pool = new ArrayList<Integer>();
        int col = n%no;
        if(col==0) col=no;

        int row = (int)( Math.ceil(n/(double)no) );

        int box = getBox(row,col);

        //System.out.println(" cell " + n);
        //System.out.println(n + " " + row + " " + col);
        for(int i=1;i<=no;i++){
            if( (rows[row].contains(i) && cols[col].contains(i)) && boxes[box].contains(i)){
                //System.out.print(i + " ");
                pool.add(i);
            }
        }
        //System.out.println();
        int ran;
        Integer sel=0;
        boolean ok = false;
        if(pool.size()==0) return false;
        while(!ok){
            //System.out.println("loop in cell : " + n);
            if(pool.size()==0){
                //System.out.println("wrong +" + n);
                return false;
            }else{
                ran = (int)( Math.ceil(Math.random()*(pool.size()-1)) );
                sel = pool.get(ran);
                board[n] = sel;

                rows[row].remove((Integer)sel);
                cols[col].remove((Integer)sel);
                boxes[box].remove((Integer)sel);
                pool.remove(sel);

                if(n==(no*no)){
                    return true;
                }else{
                    ok = generate(n+1);
                    if(!ok){
                        //System.out.println("not okay in " + n);
                        rows[row].add(sel);
                        cols[col].add(sel);
                        boxes[box].add(sel);
                    }
                }
            }
        }

        return true;
    }

    private void resetBoard(){
        cols = new ArrayList[no+1];
        rows = new ArrayList[no+1];
        boxes = new ArrayList[no+1];

        for(int i=1;i<=no;i++){
            ArrayList<Integer> ary = new ArrayList<Integer>();
            ArrayList<Integer> ary2 = new ArrayList<Integer>();
            ArrayList<Integer> ary3 = new ArrayList<Integer>();
            for(int j=0;j<=no;j++){
                ary.add(j);
                ary2.add(j);
                ary3.add(j);
            }
            cols[i] = ary;
            rows[i] = ary2;
            boxes[i] = ary3;
        }

    }




    private boolean isAvailable(int puzzle[][], int row, int col, int num)
    {
        int rowStart = (row/3) * 3;
        int colStart = (col/3) * 3;
        int i, j;

        for(i=0; i<9; ++i)
        {
            if (puzzle[row][i] == num) return false;
            if (puzzle[i][col] == num) return false;
            if (puzzle[rowStart + (i % 3)][colStart + (i / 3)] == num) return false;
        }
        return true;
    }

    private boolean fillSudoku(int puzzle[][], int row, int col)
    {
        int i;
        if(row<9 && col<9)
        {
            if(puzzle[row][col] != 0)
            {
                if((col+1)<9) return fillSudoku(puzzle, row, col+1);
                else if((row+1)<9) return fillSudoku(puzzle, row+1, 0);
                else return true;
            }
            else
            {
                for(i=0; i<9; ++i)
                {
                    if(isAvailable(puzzle, row, col, i+1))
                    {
                        puzzle[row][col] = i+1;
                        if((col+1) < 9) {
                            if(fillSudoku(puzzle, row, col +1)) return true;
                            else puzzle[row][col] = 0;
                        }
                        else if((row+1)<9)
                        {
                            if(fillSudoku(puzzle, row+1, 0)) return true;
                            else puzzle[row][col] = 0;
                        }
                        else return true;
                    }
                }
            }
            return false;
        }
        else return true;
    }


}
