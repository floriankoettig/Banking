import exceptions.UserLoginException;
import exceptions.UserRegistrationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Benutzerverwaltung {

    public void registrieren(String benutzername, String vorname, String nachname, String passwort) throws UserRegistrationException {
        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO \"Benutzer\" (\"benutzername\", \"vorname\", \"nachname\", \"password\") VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, benutzername);
            stmt.setString(2, vorname);
            stmt.setString(3, nachname);
            stmt.setString(4, passwort);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //todo: prüfen
            throw new UserRegistrationException("Fehler bei der Benutzerregistrierung", e);
        }
    }

    public boolean anmelden(String benutzername, String passwort) throws UserLoginException {
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT \"password\" FROM \"Benutzer\" WHERE \"benutzername\" = ?")) {
            stmt.setString(1, benutzername);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
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
