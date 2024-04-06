package main.server;

import java.io.BufferedReader;
import java.io.IOException;

public class OutputThread implements Runnable{

    private BufferedReader bufferedReader;

    public OutputThread(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }
    @Override
    public void run() {
        try {
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println("Client: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
