/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Ethan Dunne
 * Section: 11am
 * Date: 11/10/19
 * Time: 11:58 AM
 *
 * Project: csci205finalproject
 * Package: model
 * Class: Square
 *
 * Description:
 *
 * ****************************************
 */
package ChessParts;

import ChessParts.ChessPieces.ChessPiece;

/**
 * Representation of a single space on a chess board
 */
public class Square {

    /** The current piece located on this square */
    private ChessPiece currentPiece;
    /** Row that this square is in */
    private final int row;
    /** Column that this square is in */
    private final int col;

    /**
     * Explicit Constructor to initialize the row and column of this square
     * @param row row
     * @param col column
     */
    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }
    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    /**
     * @return a string of the Chess format of this square's position
     */
    @Override
    public String toString() {
        final int ASCII_a_OFFSET = 97;
        return String.format("%c%d", (char) col +ASCII_a_OFFSET, row +1);
    }

    /**
     * @return the current piece if there is one, otherwise return an empty looking space
     */
    public String getStringPiece(){
        if (currentPiece != null){
            return currentPiece.toString();
        }
        return "_|";
    }
}
