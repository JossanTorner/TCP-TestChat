package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ServerBroadcast {

    static final int PORT = 8000;
    private final List<ConnectedClient> clients = new ArrayList<>();

    public ServerBroadcast() {
        try(ServerSocket serverSocket = new ServerSocket(PORT);){
            while(true) {

                Socket socket = serverSocket.accept();
                ConnectedClient connectedClient = new ConnectedClient(socket, this);
                clients.add(connectedClient);
                connectedClient.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Skickar meddelande till serverns alla anslutningar
    public synchronized void broadcastMessage(String message, ConnectedClient sender) {
        for (ConnectedClient client : clients) {
            if (client != sender) { //Skickar ej till sändaren
                client.sendMessage(message);
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
         ServerBroadcast listener = new ServerBroadcast();
    }
}
