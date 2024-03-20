package pieces;

import main.Board;
import main.Move;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Piece {

    public int col, row;
    public int xPosition, yPosition;

    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true;

    BufferedImage sheet;
    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6;

    Image sprite;

    Board board;

    public Piece(Board board) {
        this.board = board;
    }

    public void paint(Graphics2D g) {
        g.drawImage(sprite, xPosition, yPosition, null);
    }
    public boolean isValidMovement(int col, int row) {
        return true;
    }
    public boolean moveCollidesWithPiece(int col, int row) {
        return false;
    }
}
