package pieces;

import main.Board;
import main.Move;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    public Pawn(Board borad, int col, int row, boolean isWhite) {
        super(borad);
        this.col = col;
        this.row = row;
        this.xPosition = col * board.titleSize;
        this.yPosition = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5 * sheetScale, (isWhite ? 0 : sheetScale) , sheetScale, sheetScale)
                .getScaledInstance(borad.titleSize, borad.titleSize, BufferedImage.SCALE_SMOOTH);
    }


    @Override
    public boolean isValidMovement(int col, int row) {
        int colorindex = isWhite ? 1 : -1;
        if (this.col == col && row == this.row - colorindex && board.getPiece(col, row) == null) {
            return true;
        }
        if (isFirstMove && this.col == col && row == this.row - colorindex * 2
                && board.getPiece(col, row) == null && board.getPiece(col, row + colorindex) == null) {
            return true;
        }

        if (col == this.col - 1 && row == this.row - colorindex && board.getPiece(col, row) != null) {
            return true;
        }

        if (col == this.col + 1 && row == this.row - colorindex && board.getPiece(col, row) != null) {
            return true;
        }
        if (board.getTitleNum(col, row) == board.enPassantTitle && col == this.col - 1 && row == this.row - colorindex
                && board.getPiece(col, row + colorindex) != null) {
            return true;
        }
        if (board.getTitleNum(col, row) == board.enPassantTitle && col == this.col + 1 && row == this.row - colorindex
                && board.getPiece(col, row + colorindex) != null) {
            return true;
        }


        return false;
    }

}
