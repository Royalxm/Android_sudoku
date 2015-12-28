package com.sudoku.p8;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public static int[][] tab = new int[9][9];
    public static int grid[][] = new int[9][9];
    int gridWithZeros[][] = new int[9][9];
    private boolean solve = false;

    public SudokuGame() {

    }

    public void init (Grille grille, int difficulty) {
        
        int save1 = 0;
        int save2 = 0;
        int intDifficulty = 0;

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
                gridWithZeros[i2][i3] = tab[i2][i3];
            }
        }
        int max,min;
        int max2,min2;
        max = -1;
        min = -3;
        max2 = -1;
        min2 = -3;
        for(int k2 =0;k2 != 9;k2++)
        {
            if(k2%3 == 0)
            {

                max2 = max2 +3;
                min2 = min2 +3;
            }
            max = -1;
            min = -3;
            for (k=0; k != difficulty; ) { // easy ==2 ; medium == 3 ;hard == 5


                if(k%2 == 0)
                {

                    max = max +3;
                    min = min +3;
                }

                int i = randomGenerator.nextInt(max2 - min2 + 1) + min2;
                int j = randomGenerator.nextInt(9);
                save1 = gridWithZeros[i][j];
                gridWithZeros[i][j] = 0;
                i2 = randomGenerator.nextInt(max2 - min2 + 1) + min2;
                int  j2 = randomGenerator.nextInt(9);
                save2 = gridWithZeros[i2][j2];
                gridWithZeros[i2][j2] = 0;

                for (i3=0;i3!=9;i3++) {
                    for (i4=0;i4!=9;i4++) {
                        grid[i3][i4] = gridWithZeros[i3][i4];
                    }
                }



                if (!fillSudoku(grid, 0, 0)) {

                    // (i, j) was not a good choice!
                    gridWithZeros[i][j] = save1;
                    gridWithZeros[i2][j2] = save2;

                }
                else {
                    k += 1;
                }

            }
        }


        System.out.println();

        for (i2=0;i2!=9;i2++) {
            for (i3=0;i3!=9;i3++) {
                System.out.printf("%2d",grid[i2][i3]);

                if(i3%3 == 2)
                    System.out.printf("  ");
            }
            System.out.println();
            if(i2%3 == 2) System.out.println();

        }


        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j ++) {
                Cellule cell = grille.getCellTab().get(index++);
                if(gridWithZeros[i][j]!= 0) cell.setLocked(true);
                cell.setValue(gridWithZeros[i][j]);
                cell.setPos(i,j);
                // Log.d("Value", "i:"+i+" j: "+j+" value: " + tab2[i][j]);
            }
        }

    }


    public void printGrille(Grille grille) {
        int[][] tempGrid = new int[9][9];
        int index = 0;

        for (int i1=0;i1!=9;i1++) {
            for (int j1 = 0; j1 != 9; j1++) {
                tempGrid[i1][j1] = grille.getCellTab().get(index++).getValue();
            }
        }

        System.out.println();

          for (int i2=0;i2!=9;i2++) {
            for (int i3=0;i3!=9;i3++) {
                System.out.printf("%2d",tempGrid[i2][i3]);

                if(i3%3 == 2)
                    System.out.printf("  ");
            }
            System.out.println();
            if(i2%3 == 2) System.out.println();

        }
    }

    public int gridAt(int i, int j) {
        return tab[i][j];
    }


    public void solveGrille(Grille grille) {
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j ++) {
                Cellule cell = grille.getCellTab().get(index++);
                if(grid[i][j]!= 0) cell.setLocked(true);
                cell.setValue(grid[i][j]);
                cell.setPos(i,j);
            }
        }

        solve = true;
    }


    public void resetGrille(Grille grille) {
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j ++) {
                Cellule cell = grille.getCellTab().get(index++);
                if(gridWithZeros[i][j]!= 0) cell.setLocked(true);
                cell.setValue(gridWithZeros[i][j]);
                cell.setPos(i,j);
            }
        }
    }


    public boolean checkSudoku2(Grille grille, int[] cellpos, int cellvalue) {

        int[][] tempGrid = new int[9][9];
        int index = 0;

        for (int i1=0;i1!=9;i1++) {
            for (int j1 = 0; j1 != 9; j1++) {
                tempGrid[i1][j1] = grille.getCellTab().get(index++).getValue();
            }
        }
//        if(a == 0) {
//            System.out.println("new tab : ");
//            for (int i2 = 0; i2 != 9; i2++) {
//                for (int i3 = 0; i3 != 9; i3++) {
//                    System.out.printf("%2d", tempGrid[i2][i3]);
//
//                    if (i3 % 3 == 2)
//                        System.out.printf("  ");
//                }
//                System.out.println();
//                if (i2 % 3 == 2) System.out.println();
//
//            }
//        a = 1;
//        }
        tempGrid[cellpos[0]][cellpos[1]] = cellvalue;
        int[] row = new int[9];
        int[] column = new int[9];
        int[] square = new int[9];

        for (int i = 0; i < 9; i++) {
            if(i != cellpos[0])
                column[i] = tempGrid[i][cellpos[1]];

        }
        for (int j = 0; j < 9; j++) {
            if(j != cellpos[1])
                row[j] = tempGrid[cellpos[0]][j];

        }

        for (int j = 0; j < 9; j++) {



            if(!(((cellpos[1] / 3) * 3 + j % 3) == cellpos[1] && ((cellpos[0] / 3) * 3 + j / 3) == cellpos[0]))
            {
                //  System.out.printf("%2d %2d et %d %d\n", (cellpos[0] / 3) * 3 + j / 3,(cellpos[1] / 3) * 3 + j % 3,cellpos[0],cellpos[1]);
                square[j] = tempGrid[(cellpos[0] / 3) * 3 + j / 3][(cellpos[1] / 3) * 3 + j % 3];
            }


        }


        if ( !validate(square,cellvalue) || !validate(row,cellvalue) ||!validate(column,cellvalue)){
            //Log.d("Value", "found :"+tempGrid[cellpos[0]][cellpos[1]]+" expected :" + tab[cellpos[0]][cellpos[1]]);
            //System.out.println("rouge");
            return false;
        }
        //System.out.println();
        return true;
    }

    private static boolean validate(int[] check,int cellval) {

         //System.out.println("chiffre : " + cellval);
        for (int i3=0;i3<check.length;i3++) {

             //System.out.printf("%2d", check[i3]);
            if(check[i3] == cellval)
                return false;
        }
        //System.out.println();
        return true;
    }

    public boolean isWon(Grille grille) {
        int i, j;
        int index=0;
        int[][] tempGrid = new int[9][9];

        if(solve) return false;

        for (i=0;i!=9;i++) {
            for (j=0;j!=9;j++) {
                tempGrid[i][j] = grille.getCellTab().get(index++).getValue();

                if(tempGrid[i][j] == grid[i][j]) continue;
                else return false;
            }
        }
        return true;
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

    public boolean fillSudoku(int puzzle[][], int row, int col)
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
