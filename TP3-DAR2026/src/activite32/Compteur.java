package activite32;

public class Compteur {
    private static int valeur = 0;

    public static synchronized int incrementer() {
        valeur++;
        return valeur;
    }

    public static synchronized int getValeur() {
        return valeur;
    }
}
