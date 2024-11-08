package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerListener {

    static final int PORT = 8000;

    public ServerListener() throws UnknownHostException {
        try(ServerSocket serverSocket = new ServerSocket(PORT);){
            while(true) {
                Socket socket = serverSocket.accept();
                BroadcastMessage broadcastMessage = new BroadcastMessage(socket);
                broadcastMessage.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
         ServerListener listener = new ServerListener();
    }
}
