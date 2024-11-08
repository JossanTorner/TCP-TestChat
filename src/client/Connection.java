package client;
import java.io.IOException;
import java.net.Socket;

public class Connection {

    Socket socket;

    public Connection(String serverIP, int port) throws IOException {
        this.socket = new Socket(serverIP, port);
    }

    public Socket getSocket() {
        return socket;
    }

    public void closeSocket() throws IOException {
        socket.close();
    }
}
