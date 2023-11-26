import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Kontoverwaltung {
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

    public void einzahlen(Connection conn, int kontonummer, double betrag) throws SQLException {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }

        try (var stmt = conn.prepareStatement(
                     "UPDATE \"Konto\" SET \"kontostand\" = \"kontostand\" + ? WHERE \"kontonummer\" = ?")) {
            stmt.setDouble(1, betrag);
            stmt.setString(2, String.valueOf(kontonummer));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Einzahlung fehlgeschlagen, Konto nicht gefunden.");
            }
        }
    }

    public void abheben(Connection conn, int kontonummer, double betrag) throws SQLException {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }

        try (var stmt = conn.prepareStatement(
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

    public void ueberweisen(Connection conn, int kontonummer, int empfaengerKontonummer, double betrag, String verwendungszweck) throws SQLException {
        if (betrag <= 0) {
            throw new IllegalArgumentException("Betrag muss positiv sein.");
        }
        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false);

            abheben(conn, kontonummer, betrag);
            einzahlen(conn, empfaengerKontonummer, betrag);

            try (var transStmt = conn.prepareStatement(
                    "INSERT INTO \"Transaktion\" (\"kontonummer\", \"empfaengerKontonummer\", \"betrag\", \"verwendungszweck\") VALUES (?, ?, ?, ?)")) {
                transStmt.setString(1, String.valueOf(kontonummer));
                transStmt.setString(2, String.valueOf(empfaengerKontonummer));
                transStmt.setDouble(3, betrag);
                transStmt.setString(4, verwendungszweck);

                transStmt.executeUpdate();
            }
            conn.commit(); //abschließen
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); //zurückrollen bei fehler
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true); //AutoCommit wieder aktivieren
            }
        }
    }
    public static void exportTransactionsByAccountNumber(int kontonummer) {
        String query = "SELECT \"timestamp\", \"kontonummer\", \"empfaengerKontonummer\", \"betrag\", \"verwendungszweck\" FROM \"Transaktion\" WHERE \"kontonummer\" = ? OR \"empfaengerKontonummer\" = ?";
        String desktopPath = "C:\\Users\\U0125812\\Desktop";
        String fileName = desktopPath + "\\Kontoauszug_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".csv";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Parameter setzen
            preparedStatement.setString(1, String.valueOf(kontonummer));
            preparedStatement.setString(2, String.valueOf(kontonummer));

            try (ResultSet resultSet = preparedStatement.executeQuery();
                 FileWriter fileWriter = new FileWriter(fileName)) {

                // CSV Header schreiben
                fileWriter.append("Transaktionsdatum;Empfänger;Sender;Verwendungszweck;Betrag;neuer Kontostand\n");

                double kontostand = 0.00;

                // Daten in die CSV-Datei schreiben
                while (resultSet.next()) {
                    double betrag = Double.parseDouble(resultSet.getString("betrag"));

                    // Berechne den Kontostand basierend auf dem Betrag
                    if (resultSet.getString("empfaengerKontonummer").equals(kontonummer)) {
                        kontostand += betrag; // Sender
                    } else {
                        kontostand -= betrag; // Empfänger
                    }

                    // Schreibe die Daten in die CSV-Datei
                    fileWriter.append(resultSet.getString("timestamp"))
                            .append(";")
                            .append(resultSet.getString("empfaengerKontonummer"))
                            .append(";")
                            .append(resultSet.getString("kontonummer"))
                            .append(";")
                            .append(resultSet.getString("verwendungszweck"))
                            .append(";")
                            .append(resultSet.getString("betrag"))
                            .append(";")
                            .append(String.valueOf(kontostand))
                            .append("\n");
                }

                System.out.println("Daten erfolgreich in die CSV-Datei exportiert: " + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isKontonummerValid(String kontonummer) {
        return kontonummer.matches("\\d{8}");
    }

    public boolean isPasswortValid(String passwort) {
        return passwort.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}");
    }

    public boolean isVerwendungszweckValid(String verwendungszweck) {
        return verwendungszweck.matches("[a-zA-Z0-9\\s]+");
    }
}