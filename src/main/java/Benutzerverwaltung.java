import exceptions.UserLoginException;
import exceptions.UserRegistrationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Benutzerverwaltung {

    private String generiereBenutzerkennung() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void registrieren(String benutzername, String vorname, String nachname, String passwort) throws UserRegistrationException {
        String benutzerkennung = generiereBenutzerkennung();
        try (var conn = DbConnection.getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO \"Benutzer\" (\"benutzerkennung\", \"benutzername\", \"vorname\", \"nachname\", \"passwort\") VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, benutzerkennung);
            stmt.setString(2, benutzername);
            stmt.setString(3, vorname);
            stmt.setString(4, nachname);
            stmt.setString(5, passwort);

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
