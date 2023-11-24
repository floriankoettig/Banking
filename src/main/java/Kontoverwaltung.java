import java.security.SecureRandom;
import java.util.UUID;

public class Kontoverwaltung {

    private double kontostand;

    public Konto erstellen(UUID idBenutzer) {
        int kontonummer = generiereKontonummer();
        double kontostand = 0.0;
        //todo: INSERT INTO TABLE "Konto"
        return new Konto(kontonummer, kontostand, idBenutzer);
    }

    private int generiereKontonummer() {
        return new SecureRandom().nextInt(90000000) + 10000000;
    }


    public double kontostandAbfragen(int kontonummer) {
        //todo: Logik, um den Kontostand des angegebenen Kontos abzufragen
        //todo: Rückgabe des Kontostands
        return 0;
    }

    public void einzahlen(int kontonummer, double betrag) {
        if (betrag > 0) {
            this.kontostand += betrag;
        } else {
            throw new IllegalArgumentException("Betrag muss positiv sein");
        }
    }

    public void abheben(int kontonummer, double betrag) {
        if (betrag > 0 && this.kontostand >= betrag) {
            this.kontostand -= betrag;
        } else {
            throw new IllegalArgumentException("Ungültiger Betrag für Abhebung");
        }
    }

    public void ueberweisungDurchfuehren(int vonKontonummer, int zuKontonummer, double betrag) {
        //todo: Logik zur Durchführung einer Überweisung
    }
}