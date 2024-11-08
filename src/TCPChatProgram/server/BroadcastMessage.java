package TCPChatProgram.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BroadcastMessage extends Thread {

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public BroadcastMessage(Socket socket) {
        this.socket = socket;
    }

    public void run(){
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String clientMessage;
            while((clientMessage = in.readLine()) != null){
                sendMessage(clientMessage);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeConnection();
        }
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
}
