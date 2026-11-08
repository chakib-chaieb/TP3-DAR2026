package activite31;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String hote = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : Serveur.PORT;
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(hote, port));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(in.readLine());
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Message (exit pour quitter) : ");
            String msg = sc.nextLine();
            out.println(msg);
            if (msg.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println(in.readLine());
        }
        socket.close();
        sc.close();
    }
}
