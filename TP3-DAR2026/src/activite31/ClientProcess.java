package activite31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {
    private Socket socket;
    private int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        System.out.println("Client n°" + numero + " connecté - IP : " + socket.getRemoteSocketAddress());
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Bienvenue, vous êtes le client n°" + numero);
            String ligne;
            while ((ligne = in.readLine()) != null) {
                if (ligne.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Client n°" + numero + " dit : " + ligne);
                out.println("Serveur -> " + ligne.toUpperCase());
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le client n°" + numero + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Client n°" + numero + " déconnecté");
        }
    }
}
