package main;

import pieces.Piece;

public class Move {

    int oldRow;
    int oldCol;
    public int newRow;
    public int newCol;

    public Piece piece;
    Piece killedPiece;


    public Move(Board board, Piece piece, int newCol, int newRow) {
        this.oldRow = piece.row;
        this.oldCol = piece.col;
        this.newRow = newRow;
        this.newCol = newCol;

        this.piece = piece;
        this.killedPiece = board.getPiece(newCol, newRow);
    }


}
