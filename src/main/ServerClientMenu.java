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

class ServerClientMenu extends JFrame {
    private JButton serverButton;
    private JButton clientButton;

    public ServerClientMenu() {
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
                try {
                    ServerSocket serverSocket = new ServerSocket(1234);
                    Server server = new Server(serverSocket);
                    server.startServer();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String serverAddress = JOptionPane.showInputDialog("Enter server IP address:");
                int serverPort = Integer.parseInt(JOptionPane.showInputDialog("Enter server port:"));
                Socket socket = null;
                try {
                    socket = new Socket(serverAddress, serverPort);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String username = JOptionPane.showInputDialog("Enter your username:");
                Client client = null;
                try {
                    client = new Client(socket, username, new Board());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                client.listenForMessages();
                client.sendMessage();
            }
        });

        panel.add(serverButton);
        panel.add(clientButton);

        add(panel);
    }
}