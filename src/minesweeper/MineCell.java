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

public enum MineCell {
    NOT_CHOSEN, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, MINE;

    /**
     * This convenient method returns the corresponding minesweeper.MineCell base on a number
     * @param num The number to be parsed to minesweeper.MineCell
     * @return the specific minesweeper.MineCell
     */
    public static MineCell intToMineCell(int num){
        switch (num){
            case -1: return MineCell.NOT_CHOSEN;
            case 0: return MineCell.ZERO; // chosen and has zero mine around
            case 1: return MineCell.ONE;
            case 2: return MineCell.TWO;
            case 3: return MineCell.THREE;
            case 4: return MineCell.FOUR;
            case 5:	return MineCell.FIVE;
            case 6: return MineCell.SIX;
            case 7: return MineCell.SEVEN;
            case 8: return MineCell.EIGHT;
            case 9: return MineCell.MINE;
            default: return null;
        }
    }

    /**
     * This convenient method return the corresponding int from a mineCell value
     * @param cell The cell to be parsed to number
     * @return the specific integer
     */
    public static int mineCelltoInt (MineCell cell){
        switch (cell){
            case ZERO: 	return 0;
            case ONE: 	return 1;
            case TWO: 	return 2;
            case THREE: return 3;
            case FOUR: 	return 4;
            case FIVE: 	return 5;
            case SIX: 	return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            default: return -1;
        }
    }
}
