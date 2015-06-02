package minesweeper;

import aima.core.logic.propositional.algorithms.KnowledgeBase;
import aima.core.logic.propositional.algorithms.PLResolution;

import java.util.ArrayList;

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

public class AIUnit {

    KnowledgeBase kb = new KnowledgeBase();
    PLResolution prover = new PLResolution();
    ArrayList<String> hasMine = new ArrayList<String>();
    ArrayList<String> noMine = new ArrayList<String>();
    ArrayList<String> onCheckedCell = new ArrayList<String>();
    boolean bits[] = new boolean[300];
    int cellValue=0;

    private static AIUnit aIUnit;

    private MineModel mineModel;

    public static AIUnit getInstance(MineModel mineModel){
        if(aIUnit==null){
            aIUnit = new AIUnit(mineModel);
        }
        if(aIUnit.mineModel!=mineModel){
            aIUnit.mineModel = mineModel;
        }
        return aIUnit;
    }

    public AIUnit(MineModel mineModel){
        this.mineModel = mineModel;

    }

    public boolean checkHasMineAt(int row,int col){
        kb=new KnowledgeBase();
        for (int i=0; i<hasMine.size(); i++) kb.tell("("+hasMine.get(i)+")");
        for (int i=0; i<noMine.size(); i++) kb.tell("(NOT "+noMine.get(i)+")");
        //----------
        for (int i=0; i<mineModel.getMaxRow(); i++)
            for (int j=0; j<mineModel.getMaxCol(); j++){
                MineCell cell=mineModel.getCellAt(i, j);
                if (cell!=MineCell.NOT_CHOSEN && cell!=MineCell.MINE && cell!=MineCell.ZERO){
                    cellValue= MineCell.mineCelltoInt(cell);
                    for (int k=0; k<onCheckedCell.size(); k++) System.out.print(onCheckedCell.get(k)+" ");
                    checkingCell(i,j);
                    System.out.println();
                    System.out.println(i+" "+j+" "+onCheckedCell.size()+" value "+cellValue);
                    binaryGenerate(1);
                }
            }
        for (int i=0; i<hasMine.size(); i++) kb.tell("("+hasMine.get(i)+")");
        for (int i=0; i<noMine.size(); i++) kb.tell("(NOT "+noMine.get(i)+")");
        //----------
        String currentCell="c"+CellToString(row,col);
        if (prover.plResolution(kb,currentCell))
            hasMine.add(currentCell);

        else if (prover.plResolution(kb,"NOT "+currentCell))
            noMine.add(currentCell);

        else noMine.add(currentCell);
        System.out.println("\n hasMine:\n");
        for (int i=0; i<hasMine.size(); System.out.print(hasMine.get(i)+" "),i++);
        System.out.println("\n noMine:\n");
        for (int i=0; i<noMine.size(); System.out.print(noMine.get(i)+" "),i++);



        String cellX="c"+CellToString(row,col);
        if (hasMine.contains(cellX)) {
            System.out.println("contain bomb");
            return true;
        }
        else if (noMine.contains(cellX)) {
            System.out.println("no bomb");
            return false;
        }
        return false;
    }

    private String CellToString(int row, int col){
        String rowS = Integer.toString(row), colS = Integer.toString(col);
        if (row<10) rowS="0"+Integer.toString(row);
        if (col<10) colS="0"+Integer.toString(col);
        return rowS+colS;
    }
    /**
     * Add cells that need to be examined to onCheckedCell[]
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     */
    private void checkingCell(int row, int col){
        onCheckedCell.clear();
        if (row!=0 && mineModel.getCellAt(row-1, col)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row-1,col));
        if (row != (mineModel.getMaxRow()-1) && mineModel.getCellAt(row+1, col)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row+1,col));
        if (col!=0 && mineModel.getCellAt(row, col-1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row,col-1));
        if (col != (mineModel.getMaxCol()-1) && mineModel.getCellAt(row, col+1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row,col+1));
        if (row!=(mineModel.getMaxRow()-1)&&col!=(mineModel.getMaxCol()-1)&&mineModel.getCellAt(row+1, col+1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row+1,col+1));
        if (row!=0 && col!=(mineModel.getMaxCol()-1)&&mineModel.getCellAt(row-1, col+1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row-1,col+1));
        if (row!=(mineModel.getMaxRow()-1) && col!=0&&mineModel.getCellAt(row+1, col-1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row+1,col-1));
        if (row!=0 && col!=0&&mineModel.getCellAt(row-1, col-1)==MineCell.NOT_CHOSEN)
            onCheckedCell.add("c"+CellToString(row-1,col-1));
    }
    private void addPremise(){
        String premise="";
        int i;
        for (i=1; i<=onCheckedCell.size(); premise+="(",i++);
        for (i=1; i<onCheckedCell.size(); i++){
            if (bits[i]) premise+=(onCheckedCell.get(i-1)+") OR ");
            else premise+=("NOT "+onCheckedCell.get(i-1)+") OR ");
        }
        if (bits[i]) premise+=(onCheckedCell.get(i-1)+")");
        else premise+=("NOT "+onCheckedCell.get(i-1)+")");
        System.out.println(premise);
        kb.tell(premise);

    }
    /**
     * count false value from array bits[]
     * @return number of false value
     */
    private int count(){
        int c=0;
        for (int i=1; i<=onCheckedCell.size(); i++) if (!bits[i]) c++;
        return c;
    }
    private void binaryGenerate(int i){
        for (int j=0; j<=1; j++){
            if (j==0) bits[i]=false; else bits[i]=true;
            if (i==onCheckedCell.size() && count()!=cellValue) addPremise();
            else if (i!=onCheckedCell.size()) binaryGenerate(i+1);
        }
    }



}
