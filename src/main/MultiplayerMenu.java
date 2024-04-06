package main;

import main.server.Client;
import main.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class MultiplayerMenu extends JFrame {
    private JButton serverButton;
    private JButton clientButton;

    public MultiplayerMenu() {
        setTitle("Multiplayer Menu");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));

        serverButton = new JButton("Create Server");
        clientButton = new JButton("Connect to Server");

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverButton.setEnabled(false);
                createServerInBackground();
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientButton.setEnabled(false);
                connectToServerInBackground();
            }
        });

        panel.add(serverButton);
        panel.add(clientButton);

        add(panel);
    }

    private void createServerInBackground() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    ServerSocket serverSocket = new ServerSocket(1234);
                    Server server = new Server(serverSocket);
                    server.startServer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }

    private void connectToServerInBackground() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Socket socket = new Socket("localhost", 1234);
                    GameWindow.open();
                    String username = JOptionPane.showInputDialog("Enter your username:");
                    Board board = new Board(); // Create your game board instance
                    Client client = new Client(socket, username, board);
                    client.listenForMessages();
                    client.sendMessage();
                    client.listenForMoves();
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };

        worker.execute();
    }
}