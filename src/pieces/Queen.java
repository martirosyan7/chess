package pieces;

import main.Board;
import main.Move;

import java.awt.image.BufferedImage;

public class Queen extends Piece{
    public Queen(Board borad, int col, int row, boolean isWhite) {
        super(borad);
        this.col = col;
        this.row = row;
        this.xPosition = col * board.titleSize;
        this.yPosition = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Queen";

        this.sprite = sheet.getSubimage(1 * sheetScale, (isWhite ? 0 : sheetScale) , sheetScale, sheetScale)
                .getScaledInstance(borad.titleSize, borad.titleSize, BufferedImage.SCALE_SMOOTH);
    }


    @Override
    public boolean isValidMovement(int col, int row) {
        return this.col == col || this.row == row || (Math.abs(col - this.col) == Math.abs(row - this.row));
    }


    @Override
    public boolean moveCollidesWithPiece(int col, int row) {

        if (this.row == row || this.col == col) {

            if (this.col > col)
                for (int c = this.col - 1; c > col; c--) {
                    if (board.getPiece(c, this.row) != null) {
                        return true;
                    }
                }

            if (this.col < col)
                for (int c = this.col + 1; c < col; c++) {
                    if (board.getPiece(c, this.row) != null) {
                        return true;
                    }
                }

            if (this.row > row)
                for (int r = this.row - 1; r > row; r--) {
                    if (board.getPiece(this.col, r) != null) {
                        return true;
                    }
                }

            if (this.row < row)
                for (int r = this.row + 1; r < row; r++) {
                    if (board.getPiece(this.col, r) != null) {
                        return true;
                    }
                }
        } else {
            if (this.col > col && this.row > row)
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.getPiece(this.col - i, this.row - i) != null) {
                        return true;
                    }
                }
            if (this.col < col && this.row > row)
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.getPiece(this.col + i, this.row - i) != null) {
                        return true;
                    }
                }
            if (this.col > col && this.row < row)
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.getPiece(this.col - i, this.row + i) != null) {
                        return true;
                    }
                }
            if (this.col < col && this.row < row)
                for (int i = 1; i < Math.abs(this.col - col); i++) {
                    if (board.getPiece(this.col + i, this.row + i) != null) {
                        return true;
                    }
                }
        }

        return false;
    }
}
