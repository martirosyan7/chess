package pieces;

import main.Board;
import main.Move;

import java.awt.image.BufferedImage;

public class Bishop extends Piece{

    public Bishop(Board borad, int col, int row, boolean isWhite) {
        super(borad);
        this.col = col;
        this.row = row;
        this.xPosition = col * board.titleSize;
        this.yPosition = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Bishop";

        this.sprite = sheet.getSubimage(2 * sheetScale, (isWhite ? 0 : sheetScale) , sheetScale, sheetScale)
                .getScaledInstance(borad.titleSize, borad.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return (Math.abs(col - this.col) == Math.abs(row - this.row));
    }


    @Override
    public boolean moveCollidesWithPiece(int col, int row) {
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

        return false;
    }
}
