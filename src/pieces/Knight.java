package pieces;

import main.Board;

import java.awt.image.BufferedImage;

public class Knight extends Piece{
    public Knight(Board borad, int col, int row, boolean isWhite) {
        super(borad);
        this.col = col;
        this.row = row;
        this.xPosition = col * board.titleSize;
        this.yPosition = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "Knight";

        this.sprite = sheet.getSubimage(3 * sheetScale, (isWhite ? 0 : sheetScale) , sheetScale, sheetScale)
                .getScaledInstance(borad.titleSize, borad.titleSize, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}
