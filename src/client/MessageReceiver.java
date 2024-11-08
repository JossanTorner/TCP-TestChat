package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class MessageReceiver{

    private Socket socket;
    private BufferedReader in;
    JTextArea textArea;


    public MessageReceiver(JTextArea textArea, Socket socket) {

        this.textArea = textArea;
        this.socket = socket;
        try{
            openStream();
            startListeningForMessages();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openStream() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void closeStream() throws IOException {
        in.close();
    }

    public void startListeningForMessages() {
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    textArea.append(serverMessage + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
