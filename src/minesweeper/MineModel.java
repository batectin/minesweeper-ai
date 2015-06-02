package minesweeper;
/**
 *    Copyright 2015 Felix & 3T
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 **/

import java.util.Random;

public class MineModel {

    /* The maximum number of rows of the miner field  */
    private final int MAX_ROW = 10;

    /* The maximum number of columns of the miner field */
    private final int MAX_COLUMN = MAX_ROW;

    /* The visible cells on the miner field  array*/
    private MineCell[][] mineCells;

    /* The mine field */
    private boolean[][] mines;

    /* The total number of mines */
    private int mineCount;

    /** @return the maximum row of the mine field */
    public final int getMaxRow(){
        return MAX_ROW;
    }

    /** @return the maximum column of the mine field */
    public final int getMaxCol(){
        return MAX_COLUMN;
    }

    /**
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     * @return whether the cell at x and y has a mine **/
    public boolean hasMineAt(int row, int col){
        return mines[row][col];
    }

    /**
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     * @return Whether the cell is NOT_CHOSEN or has chosen with the number of
     * mine(s) around it
     */
    public MineCell getCellAt(int row, int col){
        return mineCells[row][col];
    }

    /**
     * Change the cell data
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     * @param cellData the data to be set
     */
    public void setCellAt(int row,int col, MineCell cellData){
        mineCells[row][col] = cellData;
    }

    /**
     * @return the number of cells that don't have mines on the minefield
     */
    public int getNonMineCount(){
        return MAX_COLUMN*MAX_ROW - mineCount;
    }

    /** Randommize the mine field
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     * @param random the random instance to randomize the value */
    private void setRandomHasMineAt(int row, int col, Random random){
        if(random.nextInt(3000)<500){
            mines[row][col] = true;
            mineCount++;
        }else{
            mines[row][col]=false;
        }
    }

    /** constructor */
    public MineModel(){
        mineCells = new MineCell[MAX_ROW][MAX_COLUMN];
        mines = new boolean[MAX_ROW][MAX_COLUMN];
        mineCount = 0;

        Random random = new Random();
        for ( int i = 0 ; i < MAX_ROW ; i ++ ) {
            for( int j = 0 ; j < MAX_COLUMN ; j++ ) {
                mineCells[i][j] = MineCell.NOT_CHOSEN;
                setRandomHasMineAt(i,j,random);
                System.out.print((mines[i][j]==true?"*":" ")+" ");
            }
            System.out.println();
        }
    }

}
