package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {

    public int titleSize = 85;

    int cols = 8;
    int rows = 8;

    ArrayList<Piece> pieces = new ArrayList<>();

    public Piece selectedPiece;

    public int enPassantTitle = -1;


    CheckScanner checkScanner = new CheckScanner(this);

    Input input = new Input(this);

    public Board() {
        this.setPreferredSize(new Dimension(cols * titleSize, rows * titleSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPiece();
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }


    public void makeMove(Move move) {


        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else {
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPosition = move.newCol * titleSize;
            move.piece.yPosition = move.newRow * titleSize;


            capturePiece(move.killedPiece);

            move.piece.isFirstMove = false;
        }
    }


    private void movePawn(Move move) {

        int colorIndex = move.piece.isWhite ? 1 : -1;

        if (getTitleNum(move.newCol, move.newRow) == enPassantTitle) {
            move.killedPiece = getPiece(move.newCol, move.newRow + colorIndex);
        }

        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassantTitle = getTitleNum(move.newCol, move.newRow + colorIndex);
        } else {
            enPassantTitle = -1;
        }

        colorIndex = move.piece.isWhite ? 0 : 7;
        if (move.newRow == colorIndex) {
            promotePawn(move);
        }

        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPosition = move.newCol * titleSize;
        move.piece.yPosition = move.newRow * titleSize;


        capturePiece(move.killedPiece);

        move.piece.isFirstMove = false;
    }

    private void promotePawn(Move move) {
        pieces.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capturePiece(move.piece);
    }


    public void capturePiece(Piece piece) {
        pieces.remove(piece);
    }

    public boolean isValidMove(Move move) {

        Piece piece = getPiece(move.piece.row, move.piece.col);

        if (sameTeam(move.piece, move.killedPiece)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        if (checkScanner.isKingChecked(move)) {
            return false;
        }

        return true;
    }




    public boolean sameTeam(Piece piece, Piece piece2) {
        if (piece == null || piece2 == null) {
            return false;
        }
        return piece.isWhite == piece2.isWhite;
    }


    public int getTitleNum(int col, int row) {
        return row * rows + col;
    }


    Piece findKing(boolean isWhite) {
        for (Piece piece : pieces) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }


    public void addPiece() {
        pieces.add(new Knight(this, 1, 7, true));
        pieces.add(new Knight(this, 6, 7, true));

        pieces.add(new Rook(this, 7, 7, true));
        pieces.add(new Rook(this, 0, 7, true));

        pieces.add(new Bishop(this, 5, 7, true));
        pieces.add(new Bishop(this, 2, 7, true));

        pieces.add(new Queen(this, 3, 7, true));
        pieces.add(new King(this, 4, 7, true));

        pieces.add(new Pawn(this, 0, 6, true));
        pieces.add(new Pawn(this, 1, 6, true));
        pieces.add(new Pawn(this, 2, 6, true));
        pieces.add(new Pawn(this, 3, 6, true));
        pieces.add(new Pawn(this, 4, 6, true));
        pieces.add(new Pawn(this, 5, 6, true));
        pieces.add(new Pawn(this, 6, 6, true));
        pieces.add(new Pawn(this, 7, 6, true));


        pieces.add(new Knight(this, 1, 0, false));
        pieces.add(new Knight(this, 6, 0, false));

        pieces.add(new Rook(this, 7, 0, false));
        pieces.add(new Rook(this, 0, 0, false));

        pieces.add(new Bishop(this, 5, 0, false));
        pieces.add(new Bishop(this, 2, 0, false));

        pieces.add(new Queen(this, 3, 0, false));
        pieces.add(new King(this, 4, 0, false));

        pieces.add(new Pawn(this, 0, 1, false));
        pieces.add(new Pawn(this, 1, 1, false));
        pieces.add(new Pawn(this, 2, 1, false));
        pieces.add(new Pawn(this, 3, 1, false));
        pieces.add(new Pawn(this, 4, 1, false));
        pieces.add(new Pawn(this, 5, 1, false));
        pieces.add(new Pawn(this, 6, 1, false));
        pieces.add(new Pawn(this, 7, 1, false));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(227, 169, 114, 255));
                } else {
                    g.setColor(new Color(169, 114, 65, 255));
                }
                g.fillRect(i * titleSize, j * titleSize, titleSize, titleSize);
            }
        }

        if (selectedPiece != null)
            for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isValidMove(new Move(this, selectedPiece, c, r))) {
                    g.setColor(new Color(68, 180, 57, 190));
                    g.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                }
            }
        }
        
        for (Piece piece : pieces) {
            piece.paint((Graphics2D) g);
        }
    }
}
