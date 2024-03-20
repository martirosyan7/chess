package pieces;

import main.Board;
import main.Move;

import java.awt.image.BufferedImage;

public class King extends Piece{
    public King(Board borad, int col, int row, boolean isWhite) {
        super(borad);
        this.col = col;
        this.row = row;
        this.xPosition = col * board.titleSize;
        this.yPosition = row * board.titleSize;

        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(0 * sheetScale, (isWhite ? 0 : sheetScale) , sheetScale, sheetScale)
                .getScaledInstance(borad.titleSize, borad.titleSize, BufferedImage.SCALE_SMOOTH);
    }


    @Override
    public boolean isValidMovement(int col, int row) {
        return Math.abs((col - this.col) * (row - this.row)) == 1 ||
                Math.abs(col - this.col) + Math.abs(row - this.row) == 1;
    }
}
