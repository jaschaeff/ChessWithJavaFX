/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: James Campbell
 * Section: 11 am
 * Date: 11/10/19
 * Time: 1:42 PM
 *
 * Project: CSCI205FinalProject
 * Package: ChessParts.ChessPieces
 * Class: Queen
 *
 * Description:
 * A Queen piece class that holds the team of queen and can calculate the moves. Queen can move diagnolly as many pieces
 * and horizontally or vertically as many pieces.
 * ****************************************
 */
package ChessParts.ChessPieces;

import ChessParts.ChessBoard;
import ChessParts.Square;
import ChessParts.Team;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {
    public Queen(Team team) {
        super(team);
    }

    /**
     * Will return an ArrayList with square positions of all the possible that
     * a specific chess piece is allowed to move to
     * @param currentSquare, the position the chess piece is on the board
     * @param  board, the board to check for it's legal positions on
     * @return ArrayList of all the possible moves
     */
    @Override
    public List<Square> getLegalMoves(Square currentSquare, ChessBoard board) {
        ArrayList<Square> allMoves = getAllMoves(currentSquare, board);
        List<Square> legalMoves = new ArrayList<>();


//        int xCoordinate = Position[0];
//        int yCoordinate = Position[1];
//        ArrayList<int[]> moves = new ArrayList<>(DIRECTIONS);
//
        return legalMoves;
    }

    private ArrayList<Square> getAllMoves(Square currentSquare, ChessBoard board) {
        ArrayList<Square> allMoves = new ArrayList<>();
        int row = currentSquare.getRow();
        int col = currentSquare.getCol();
        for (int a = 0 ; a < DIRECTIONS; a ++){
            Square option1 = {row + a, col};
            Square option2 = {row - a, col};
            Square option3 = {row, Position[1] + a};
            Square option4 = {Position[0], Position[1] - a};
            moves.add(option1);
            moves.add(option2);
            moves.add(option3);
            moves.add(option4);
        }
        for (int i = 0; i < DIRECTIONS - xCoordinate; i ++){
            int[] option = {Position[0] + i, Position[1]};
            moves.add(option);
        }
        for (int j = 0; j < xCoordinate; j ++){
            int[] option = {Position[0] - j, Position[1]};
            moves.add(option);
        }
        for (int k = 0; k < DIRECTIONS - yCoordinate; k ++){
            int[] option = {Position[0], Position[1] + k};
            moves.add(option);
        }
        for (int l = 0; l < yCoordinate; l ++){
            int[] option = {Position[0], Position[1] - l};
            moves.add(option);
        }
    }

    @Override
    public String toString() {
        return "Q" + team.toString().substring(0,1);
    }
}