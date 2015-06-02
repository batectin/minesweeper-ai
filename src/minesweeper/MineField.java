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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MineField extends JFrame {

    enum GameState {
        WIN,
        GAME_OVER,
        PLAYING
    }

    private GameState gameState;

    /* The total number of Cells that are not mines and are selected */
    private int nonMineSelected;

    /* Data Model for the game */
    private MineModel mineModel = new MineModel();

    /* Mine Cells represent by Buttons*/
    private JLabel[][] mineCells;
    private final int MINE_CELL_HEIGHT = 30;
    private final int MINE_CELL_WIDTH = 30;
    private final ImageIcon IDLE_MINE_CELL_ICON = scaleImageToIcon(File.separator+ "img/idle_mine_cell.png"
            ,MINE_CELL_WIDTH,MINE_CELL_HEIGHT);
    private final ImageIcon MOUSE_OVER_MINE_CELL_ICON = scaleImageToIcon(File.separator+ "img/mouse_over_mine_cell.png",
            MINE_CELL_WIDTH,MINE_CELL_HEIGHT);
    private final ImageIcon SELECTED_MINE_CELL_ICON = scaleImageToIcon(File.separator+ "img/selected_mine_cell.png",
            MINE_CELL_WIDTH,MINE_CELL_HEIGHT);
    private final ImageIcon MINE_ICON = scaleImageToIcon(File.separator+ "img/mine_icon.jpg",MINE_CELL_WIDTH,MINE_CELL_HEIGHT);
    /* Text Area for AI Unit */
    JLabel aiLabel;

    /* Restart button */
    private JLabel restartLabel;
    private final int RESTART_LABEL_WIDTH = 30;
    private final int RESTART_LABEL_HEIGHT = 30;
    private final ImageIcon PLAYING_RESTART_LABEL_ICON = scaleImageToIcon(File.separator+ "img/playing_restart_button.png"
            ,RESTART_LABEL_WIDTH,RESTART_LABEL_HEIGHT);
    private final ImageIcon GAME_OVER_RESTART_BUTTON = scaleImageToIcon(File.separator+ "img/game_over_restart_button.png",
            RESTART_LABEL_WIDTH,RESTART_LABEL_HEIGHT);
    private final ImageIcon WIN_RESTART_BUTTON = scaleImageToIcon(File.separator+ "img/win_restart_button.png",
            RESTART_LABEL_WIDTH,RESTART_LABEL_HEIGHT);

    public MineField(){
        gameState = GameState.PLAYING;
        mineCells = new JLabel[mineModel.getMaxCol()][mineModel.getMaxRow()];
        nonMineSelected = 0 ;

        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        this.setTitle("Minesweeper");


        GridLayout gridLayout = new GridLayout(mineModel.getMaxRow(), mineModel.getMaxCol());
        JPanel mineFieldPanel = new JPanel();
        mineFieldPanel.setLayout(gridLayout);
        for (int i = 0 ; i < mineModel.getMaxRow();i++){
            for(int j = 0 ; j < mineModel.getMaxCol(); j++){
                mineCells[i][j] = new JLabel();
                mineCells[i][j].setIconTextGap(-MINE_CELL_WIDTH/2 -4); // Align the text inside the center of JLabel
                setMineCellAt(i, j, MineCell.NOT_CHOSEN);
                setMineCellListener(i,j);
                mineFieldPanel.add(mineCells[i][j]);
            }
        }

        JPanel aiAndRestartPanel = new JPanel();
        aiAndRestartPanel.setLayout(new BorderLayout());

        aiLabel = new JLabel("Right Click = Prediction");
        aiLabel.setIconTextGap(+10);
        aiLabel.setHorizontalAlignment(JLabel.CENTER);

        restartLabel = new JLabel();
        restartLabel.setIcon(PLAYING_RESTART_LABEL_ICON);
        restartLabel.setPreferredSize(new Dimension(RESTART_LABEL_WIDTH + 30, RESTART_LABEL_HEIGHT));
        restartLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                restart();

            }
        });

        aiAndRestartPanel.add(aiLabel, BorderLayout.CENTER);
        aiAndRestartPanel.add(restartLabel, BorderLayout.EAST);
        aiAndRestartPanel.setPreferredSize(new Dimension(mineModel.getMaxCol() * MINE_CELL_WIDTH, 50));

        this.add(aiAndRestartPanel);
        this.add(mineFieldPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    }

    private void restart(){
        mineModel = new MineModel();
        for(int i = 0; i < mineModel.getMaxRow();i++){
            for(int j = 0 ; j < mineModel.getMaxCol();j++){
                setMineCellAt(i,j,MineCell.NOT_CHOSEN);
            }
        }
        nonMineSelected = 0 ;
        gameState = GameState.PLAYING;
        restartLabel.setIcon(PLAYING_RESTART_LABEL_ICON);
    }
    private String toColorizedNum(int num){
        String color;
        switch(num){
            case 1:
                color = "green";
                break;
            case 2:
                color = "blue";
                break;
            case 3:
                color = "purple";
                break;
            case 4:
                color = "red";
                break;
            case 5:
                color = "brown";
                break;
            case 6:
                color = "pink";
                break;
            case 7:
                color = "black";
                break;
            case 8:
                color = "orange";
                break;

            default:
                color ="white";
        }
        return "<html><font color='"+color+"'>"+num+"</font></html>";
    }
    /**
     * Update the cell to the GUI and data model
     * Call when the data model of the cell is changed.
     * @param row the row at which the cell is located
     * @param col the column at which the cell is located
     * @param mineCell the cell data needed to be set
     */
    private void setMineCellAt(int row,int col,MineCell mineCell){
        JLabel cell = mineCells[row][col];
        mineModel.setCellAt(row,col,mineCell);
        switch(mineCell) {
            case MINE:
                cell.setText(null);
                cell.setIcon(MINE_ICON);
                break;
            case NOT_CHOSEN:
                cell.setText(null);
                cell.setIcon(IDLE_MINE_CELL_ICON);
                break;
            case ZERO:
                cell.setText(null);
                cell.setIcon(SELECTED_MINE_CELL_ICON);
                break;
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
            case EIGHT:
                cell.setText(toColorizedNum(mineModel.getCellAt(row, col).ordinal() - 1));
                cell.setIcon(SELECTED_MINE_CELL_ICON);
                break;
        }
    }



    private void spreadingCellSet(int row, int col){
        nonMineSelected ++;
        setMineCellAt(row, col, MineCell.intToMineCell(countMines(row, col)));
        if ((mineModel.getCellAt(row, col)!=MineCell.ZERO &&
                mineModel.getCellAt(row, col)!=MineCell.NOT_CHOSEN &&
                mineModel.getCellAt(row,col)!=MineCell.MINE)){
            return;
        }
        if (row!=0 && mineModel.getCellAt(row-1, col)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row - 1, col);
        if (row != (mineModel.getMaxRow()-1) && mineModel.getCellAt(row+1, col)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row + 1, col);
        if (col!=0 && mineModel.getCellAt(row, col-1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row, col - 1);
        if (col != (mineModel.getMaxCol()-1) && mineModel.getCellAt(row, col+1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row, col + 1);
        if (row!=(mineModel.getMaxRow()-1)&&col!=(mineModel.getMaxCol()-1)&& mineModel.getCellAt(row+1, col+1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row + 1, col + 1);
        if (row!=0 && col!=(mineModel.getMaxCol()-1)&& mineModel.getCellAt(row-1, col+1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row - 1, col + 1);
        if (row!=(mineModel.getMaxRow()-1) && col!=0&& mineModel.getCellAt(row+1, col-1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row + 1, col - 1);
        if (row!=0 && col!=0&& mineModel.getCellAt(row-1, col-1)==MineCell.NOT_CHOSEN)
            spreadingCellSet(row - 1, col - 1);
    }
    private int boolToInt(boolean x){
        return x?1:0;
    }

    /**
     * Counts the number of mines that are located around that cell
     * @param row the row where the cell is located
     * @param col the column where the cell is located
     * @return the number of mines around the cell
     */
    private int countMines(int row, int col){

        return  ((row!=0)? boolToInt(mineModel.hasMineAt(row-1, col)):0) +
                ((row != (mineModel.getMaxRow()-1))?boolToInt(mineModel.hasMineAt(row+1, col)):0)+
                ((col!=0)? boolToInt(mineModel.hasMineAt(row, col-1)):0)+
                ((col != (mineModel.getMaxCol()-1))?boolToInt(mineModel.hasMineAt(row, col+1)):0)+
                ((row!=0 && col!=0)?boolToInt(mineModel.hasMineAt(row-1, col-1)):0)+
                ((row!=(mineModel.getMaxRow()-1)&&col!=(mineModel.getMaxCol()-1))?
                        boolToInt(mineModel.hasMineAt(row+1, col+1)):0)+
                ((row!=0 && col!=(mineModel.getMaxCol()-1))?
                        boolToInt(mineModel.hasMineAt(row-1, col+1)):0)+
                ((row!=(mineModel.getMaxRow()-1) && col!=0)?boolToInt(mineModel.hasMineAt(row+1, col-1)):0);
    }

    /**
     * This method scale the Image to an arbitrary size
     * @param URL the link that directly points to the resource
     * @param width the width of the scaled resource
     * @param height the height of the scaled resource
     * @return the ImageIcon that is scaled
     */
    private ImageIcon scaleImageToIcon(String URL, int width, int height){
        Image image;
        try {
            image = ImageIO.read(getClass().getResource(URL));
            return new ImageIcon(image.getScaledInstance(width,height,Image.SCALE_SMOOTH));
        } catch (IOException e) {
            System.out.println("Cannot access resource!");
            return null;
        }
    }

    private void setMineCellListener(int row,int col){
        JLabel cell = mineCells[row][col];
        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (mineModel.getCellAt(row, col) == MineCell.NOT_CHOSEN) {
                    cell.setIcon(MOUSE_OVER_MINE_CELL_ICON);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (mineModel.getCellAt(row, col) == MineCell.NOT_CHOSEN)
                    cell.setIcon(IDLE_MINE_CELL_ICON);
                if(aiLabel.getText().equals("Right Click = Prediction")){
                    aiLabel.setText("Right Click = Prediction");
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==MouseEvent.BUTTON3){ // Right mouse click
                    if(AIUnit.getInstance(mineModel).checkHasMineAt(row,col)){
                        aiLabel.setText("Mine!");
                    }else{
                        aiLabel.setText("You are safe.");
                    }
                }else if(e.getButton() == MouseEvent.BUTTON1){ // Left mouse click
                    if (mineModel.getCellAt(row, col) == MineCell.NOT_CHOSEN && gameState==GameState.PLAYING) {
                        if(mineModel.hasMineAt(row,col)){
                            //Game OVER
                            gameState = GameState.GAME_OVER;
                            restartLabel.setIcon(GAME_OVER_RESTART_BUTTON);
                            for(int i = 0; i < mineModel.getMaxRow();i++){
                                for(int  j = 0 ; j< mineModel.getMaxCol();j++){
                                    if(mineModel.hasMineAt(i,j)){
                                        setMineCellAt(i,j,MineCell.MINE);
                                    }
                                }
                            }
                        }else{
                            spreadingCellSet(row,col);
                        }
                        if(nonMineSelected == mineModel.getNonMineCount()){
                            gameState = GameState.WIN;
                            restartLabel.setIcon(WIN_RESTART_BUTTON);
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args ){
        MineField mineField = new MineField();

    }

}
