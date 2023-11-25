import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.UUID;

public class Kontoverwaltung {

    private double kontostand;

    public void erstellen(UUID idBenutzer) {
        int kontonummer = generiereKontonummer();
        double kontostand = 0.0;

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO \"Konto\" (\"kontonummer\", \"kontostand\", \"idBenutzer\") VALUES (?, ?, ?)")) {
            stmt.setString(1, String.format("%08d", kontonummer));
            stmt.setDouble(2, kontostand);
            stmt.setObject(3, idBenutzer);

            stmt.executeUpdate();
        } catch (SQLException e) {
            //todo: Excecption werfen
            e.printStackTrace();
        }
    }

    public int generiereKontonummer() {
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