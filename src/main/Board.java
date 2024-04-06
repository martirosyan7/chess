package main;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Board extends JPanel {

    public int titleSize = 85;

    int cols = 8;
    int rows = 8;


    public boolean turn = true;


    ArrayList<Piece> pieces = new ArrayList<>();

    public Piece selectedPiece;

    public int enPassantTitle = -1;


    public CheckScanner checkScanner = new CheckScanner(this);

    Input input = new Input(this);

    public Board() throws IOException {
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
        } else if(move.piece.name.equals("King")){
            moveKing(move);
        }
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.xPosition = move.newCol * titleSize;
        move.piece.yPosition = move.newRow * titleSize;


        capturePiece(move.killedPiece);

        move.piece.isFirstMove = false;

        turn = !turn;
    }


    private void moveKing(Move move) {
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            Piece rook;
            if (move.piece.col < move.newCol) {
                rook = getPiece(7, move.piece.row);
                rook.col = 5;
            } else {
                rook = getPiece(0, move.piece.row);
                rook.col = 3;
            }
            rook.xPosition = rook.col * titleSize;
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
    }

    private void promotePawn(Move move) {
        pieces.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capturePiece(move.piece);
    }


    public void capturePiece(Piece piece) {
        pieces.remove(piece);
    }

    public boolean isValidMove(Move move) {

        if (move.newRow < 0 || move.newRow >= rows || move.newCol < 0 || move.newCol >= cols) {
            return false;
        }
        if (sameTeam(move.piece, move.killedPiece)) {
            return false;
        }
        if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
            return false;
        }
        if (move.piece.moveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
        return !checkScanner.isKingChecked(move);
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

    public String moveToString(Move move) {
        return " " + getAlphanumeric(move.oldCol, move.oldRow) + "-" + getAlphanumeric(move.newCol, move.newRow);
    }

    public Move stringToMove(String moveString) {
        int oldCol = moveString.charAt(1) - 'a';
        int oldRow = 8 - (moveString.charAt(2) - '0');
        int newCol = moveString.charAt(4) - 'a';
        int newRow = 8 - (moveString.charAt(5) - '0');
        return new Move(this, getPiece(oldCol, oldRow), newCol, newRow);
    }


    public String getAlphanumeric(int col, int row) {
        if (col >= 0 && col < cols && row >= 0 && row < rows) {
            char colChar = (char) ('a' + col);
            int rowNumber = rows - row;
            return "" + colChar + rowNumber;
        } else {
            return "";
        }
    }

    public String convertToAlphanumeric(int col, int row) {
        if (col >= 0 && col < cols && row >= 0 && row < rows) {
            char colChar = (char) ('a' + col);
            int rowNumber = rows - row;
            return "" + colChar + rowNumber;
        } else {
            return "";
        }
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
                if (isValidMove(new Move(this, selectedPiece, c, r)) && turn == selectedPiece.isWhite) {
                    g.setColor(new Color(68, 180, 57, 190));
                    g.fillRect(c * titleSize, r * titleSize, titleSize, titleSize);
                }
            }
        }

        for (Piece piece : pieces) {
            if (piece.name.equals("King")) {
                Move move = new Move(this, piece, piece.col, piece.row);
                if (checkScanner.isKingChecked(move)) {
                    g.setColor(Color.RED);
                    g.fillRect(piece.col * titleSize, piece.row * titleSize, titleSize, titleSize);
                }
            }
            piece.paint((Graphics2D) g);
        }
    }

}
