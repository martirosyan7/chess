package main;

import main.server.Client;
import pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;

public class Input extends MouseAdapter {

    Board board;
    private Client client;

    public Input(Board board) throws IOException {
        this.board = board;
    }

    public Input(Board board, Client client) {
        this.board = board;
        this.client = client;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int col = e.getX() / board.titleSize;
        int row = e.getY() / board.titleSize;

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.titleSize;
        int row = e.getY() / board.titleSize;

        if (board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);
            if (board.isValidMove(move) && board.turn == board.selectedPiece.isWhite) {
                //TODO: Both of the players are clients, server is only for communication,
                // so we need to send the move to the other player which are deleted from the list of clients in the server

                board.makeMove(move);

//                client.sendMoveToServer(move);
            } else {
                board.selectedPiece.xPosition = board.selectedPiece.col * board.titleSize;
                board.selectedPiece.yPosition = board.selectedPiece.row * board.titleSize;
            }
        }
        board.selectedPiece = null;
        board.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (board.selectedPiece != null) {
            board.selectedPiece.xPosition = e.getX() - board.titleSize / 2;
            board.selectedPiece.yPosition = e.getY() - board.titleSize / 2;

            board.repaint();
        }

    }
}
