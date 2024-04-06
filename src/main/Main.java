package main;

import main.server.Client;
import main.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main extends JFrame {
    private JButton singlePlayerButton;
    private JButton multiplayerButton;

    public Main() {
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));

        singlePlayerButton = new JButton("Single Player");
        multiplayerButton = new JButton("Multiplayer");

        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    GameWindow.open();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
            }
        });

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMultiplayerMenu();

                //TODO: if multiplayer open window to select server or client.
                // If server run server, if client run client.
                // Open the game window and send moves to server.
                // Bring back main methods

                dispose();
            }
        });

        panel.add(singlePlayerButton);
        panel.add(multiplayerButton);

        add(panel);
        setVisible(true);
    }


    public void startSinglePlayerGame() throws IOException {
        JFrame frame = new JFrame();
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

        Board board = new Board();
        frame.add(board);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }


    public void openMultiplayerMenu() {
        MultiplayerMenu multiplayerMenu = new MultiplayerMenu();
        multiplayerMenu.setVisible(true);
        dispose();
    }
    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });

    }

}