package activite32;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(numero);
            out.flush();
            while (true) {
                Operation op = (Operation) in.readObject();
                calculer(op);
                int total = Compteur.incrementer();
                System.out.println("Client n°" + numero + " : " + op.getOperande1() + " " + op.getOperateur() + " "
                        + op.getOperande2() + " | compteur global = " + total);
                out.writeObject(op);
                out.flush();
            }
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur avec le client n°" + numero + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Client n°" + numero + " déconnecté");
        }
    }

    private void calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        switch (op.getOperateur()) {
            case '+':
                op.setResultat(a + b);
                break;
            case '-':
                op.setResultat(a - b);
                break;
            case '*':
                op.setResultat(a * b);
                break;
            case '/':
                if (b == 0) {
                    op.setErreur("Division par zéro");
                } else {
                    op.setResultat(a / b);
                }
                break;
            default:
                op.setErreur("Opérateur invalide : " + op.getOperateur());
        }
    }
}
