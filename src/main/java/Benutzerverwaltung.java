import exceptions.UserLoginException;
import exceptions.UserRegistrationException;

import java.sql.*;
import java.util.UUID;

public class Benutzerverwaltung {

    private String generiereBenutzerkennung() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void registrieren(String benutzername, String vorname, String nachname, String passwort) throws UserRegistrationException {
        String benutzerkennung = generiereBenutzerkennung();
        UUID idBenutzer;

        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO \"Benutzer\" (\"benutzerkennung\", \"benutzername\", \"vorname\", \"nachname\", \"passwort\") VALUES (?, ?, ?, ?, ?) RETURNING \"idBenutzer\"",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, benutzerkennung);
            stmt.setString(2, benutzername);
            stmt.setString(3, vorname);
            stmt.setString(4, nachname);
            stmt.setString(5, passwort);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Benutzererstellung fehlgeschlagen, keine Zeilen betroffen.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idBenutzer = (UUID) rs.getObject("idBenutzer");
                } else {
                    throw new SQLException("Benutzererstellung fehlgeschlagen, keine ID abrufbar.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: prüfen
            throw new UserRegistrationException("Fehler bei der Benutzerregistrierung", e);
        }
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        kontoverwaltung.erstellen(idBenutzer);
    }

    public boolean anmelden(String benutzername, String passwort) throws UserLoginException {
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT \"passwort\" FROM \"Benutzer\" WHERE \"benutzername\" = ?")) {
            stmt.setString(1, benutzername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("passwort");
                    return storedPassword.equals(passwort);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: prüfen
            throw new UserLoginException("Fehler bei der Benutzeranmeldung", e);
        }
        return false;
    }
}
