package activite31;

import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static final int PORT = 1234;

    public static void main(String[] args) throws Exception {
        int nbClients = 0;
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Serveur en attente sur le port " + PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            nbClients++;
            new ClientProcess(socket, nbClients).start();
        }
    }
}
