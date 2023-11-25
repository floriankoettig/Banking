import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Kontoverwaltung {
    private double kontostand;
    public int generiereKontonummer() {
        return new SecureRandom().nextInt(90000000) + 10000000;
    }

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
            //todo: exception handling
            e.printStackTrace();
        }
    }

    public int ermittleKontonummer(String benutzername) throws SQLException {
        int kontonummer = -1;

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "SELECT \"kontonummer\" FROM \"Konto\" JOIN \"Benutzer\" ON \"Konto\".\"idBenutzer\" = \"Benutzer\".\"idBenutzer\" WHERE \"Benutzer\".\"benutzername\" = ?")) {
            stmt.setString(1, benutzername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    kontonummer = rs.getInt("kontonummer");
                } else {
                    throw new SQLException("Kein Konto für Benutzer " + benutzername + " gefunden.");
                }
            }
        }
        return kontonummer;
    }

    public double kontostandAbfragen(int kontonummer) {
        double kontostand = 0.0;

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "SELECT \"kontostand\" FROM \"Konto\" WHERE \"kontonummer\" = ?")) {
            stmt.setString(1, String.format("%08d", kontonummer));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    kontostand = rs.getDouble("kontostand");
                } else {
                    throw new SQLException("Kein Konto mit der angegebenen Kontonummer gefunden.");
                }
            }
        } catch (SQLException e) {
            //todo: exception handling
            e.printStackTrace();
        }
        return kontostand;
    }

    public void einzahlen(int kontonummer, double betrag) throws SQLException {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE \"Konto\" SET \"kontostand\" = \"kontostand\" + ? WHERE \"kontonummer\" = ?")) {
            stmt.setDouble(1, betrag);
            stmt.setString(2, String.valueOf(kontonummer));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Einzahlung fehlgeschlagen, Konto nicht gefunden.");
            }
        }
    }

    public void abheben(int kontonummer, double betrag) throws SQLException {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE \"Konto\" SET \"kontostand\" = \"kontostand\" - ? WHERE \"kontonummer\" = ? AND \"kontostand\" >= ?")) {
            stmt.setDouble(1, betrag);
            stmt.setString(2, String.valueOf(kontonummer));
            stmt.setDouble(3, betrag);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Abhebung fehlgeschlagen, unzureichender Kontostand oder Konto nicht gefunden.");
            }
        }
    }

    public void ueberweisungDurchfuehren(int kontonummer, int empfaengerKontonummer, double betrag) {
        //todo: implementierung
    }
}