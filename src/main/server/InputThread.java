package main.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputThread implements Runnable{

    private BufferedWriter bufferedWriter;

    public InputThread(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }
    @Override
    public void run() {

        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String messageToClient = scanner.nextLine();
                bufferedWriter.write(messageToClient);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                if (messageToClient.equalsIgnoreCase("BYE")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
