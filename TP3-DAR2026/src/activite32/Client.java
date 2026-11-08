package activite32;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String hote = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : Serveur.PORT;
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(hote, port));
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        int numero = (Integer) in.readObject();
        System.out.println("Connecté au serveur - vous êtes le client n°" + numero);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Opération (ex : 5 + 3, exit pour quitter) : ");
            String ligne = sc.nextLine().trim();
            if (ligne.equalsIgnoreCase("exit")) {
                break;
            }
            String[] p = ligne.split("\\s+");
            if (p.length != 3 || p[1].length() != 1) {
                System.out.println("Format invalide, utilisez : nombre opérateur nombre");
                continue;
            }
            double a;
            double b;
            try {
                a = Double.parseDouble(p[0]);
                b = Double.parseDouble(p[2]);
            } catch (NumberFormatException e) {
                System.out.println("Nombres invalides");
                continue;
            }
            out.writeObject(new Operation(a, p[1].charAt(0), b));
            out.flush();
            Operation res = (Operation) in.readObject();
            if (res.getErreur() != null) {
                System.out.println("Erreur : " + res.getErreur());
            } else {
                System.out.println("Résultat = " + res.getResultat());
            }
        }
        socket.close();
        sc.close();
    }
}
