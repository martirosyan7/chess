package main.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    private ServerSocket serverSocket;
    private boolean running;
    private List<ClientHandler> clients;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.running = true;
        this.clients = new ArrayList<>();
    }

    public void startServer() {
        try {
            while (running) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer() {
        try {
            running = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ClientHandler> getOtherClients(ClientHandler currentClient) {
        List<ClientHandler> otherClients = new ArrayList<>(clients);
        otherClients.remove(currentClient); // Remove the current client from the list
        return otherClients;
    }
}
