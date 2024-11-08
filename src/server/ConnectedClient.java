package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient extends Thread {

    Socket socket;
    PrintWriter out;
    BufferedReader in;
    ServerBroadcast server;

    public ConnectedClient(Socket socket, ServerBroadcast server) {
        this.socket = socket;
        this.server = server;

        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }

    public String getMessage() throws IOException {
        return in.readLine();
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            String message;
            //Här tas den här trådens meddelande in
            while ((message = getMessage()) != null) {
                //Här skickas detta mottagna meddelande till serverns alla klienter
                server.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            closeConnection();
        }
    }
}
