package main;

import pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class PromotionDialog extends JDialog {
    private Piece selectedPiece;
    private HashMap<String, BufferedImage> pieceImages;

    public PromotionDialog(JFrame parent, boolean isWhite) {
        super(parent, "Choose Promotion", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        pieceImages = new HashMap<>();
        loadPieceImages(isWhite);

        JPanel panel = new JPanel(new GridLayout(1, 4));
        for (String pieceName : pieceImages.keySet()) {
            JButton button = new JButton(new ImageIcon(pieceImages.get(pieceName)));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedPiece = createPiece(pieceName, isWhite);
                    dispose();
                }
            });
            panel.add(button);
        }
        add(panel, BorderLayout.CENTER);
    }

    private void loadPieceImages(boolean isWhite) {
        // Load images for each piece option
        String[] pieceNames = {"Queen", "Rook", "Bishop", "Knight"}; // Example piece names
        for (String pieceName : pieceNames) {
            String imagePath = "/resources/" + pieceName.toLowerCase() + (isWhite ? "_white.png" : "_black.png");
            try {
                BufferedImage image = ImageIO.read(getClass().getResource(imagePath));
                pieceImages.put(pieceName, image);
            } catch (IOException | IllegalArgumentException e) {
                // Log the error or display a message to the user
                System.err.println("Error loading image: " + imagePath);
                e.printStackTrace();
                // You may want to use a default image or placeholder here
                // pieceImages.put(pieceName, defaultImage);
            }
        }
    }

    private Piece createPiece(String pieceName, boolean isWhite) {
        // Create the corresponding piece based on the chosen option
        // You may need to adjust this based on your Piece class constructors
        switch (pieceName) {
            case "Queen":
                return new Queen(null, 0, 0, isWhite); // Update parameters as needed
            case "Rook":
                return new Rook(null, 0, 0, isWhite);
            case "Bishop":
                return new Bishop(null, 0, 0, isWhite);
            case "Knight":
                return new Knight(null, 0, 0, isWhite);
            default:
                return null;
        }
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }
}
