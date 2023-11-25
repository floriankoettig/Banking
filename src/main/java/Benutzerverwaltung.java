import exceptions.UserLoginException;
import exceptions.UserRegistrationException;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Benutzerverwaltung {

    private static final Logger LOGGER = Logger.getLogger(Benutzerverwaltung.class.getName());

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
                LOGGER.log(Level.WARNING, "Benutzererstellung für {0} fehlgeschlagen, keine Zeilen betroffen.", benutzername);
                throw new SQLException("Benutzererstellung fehlgeschlagen, keine Zeilen betroffen.");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idBenutzer = (UUID) rs.getObject("idBenutzer");
                    LOGGER.log(Level.INFO, "Benutzer {0} erfolgreich registriert mit ID {1}.", new Object[]{benutzername, idBenutzer});
                } else {
                    LOGGER.log(Level.SEVERE, "Benutzererstellung für {0} fehlgeschlagen, keine ID abrufbar.", benutzername);
                    throw new SQLException("Benutzererstellung fehlgeschlagen, keine ID abrufbar.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Fehler bei der Registrierung des Benutzers " + benutzername + ".", e);
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
                    if (storedPassword.equals(passwort)) {
                        LOGGER.log(Level.INFO, "Benutzer {0} erfolgreich angemeldet.", benutzername);
                        return true; //erfolgreiche Anmeldung
                    } else {
                        LOGGER.log(Level.WARNING, "Falsches Passwort für Benutzer {0}.", benutzername);
                        throw new UserLoginException("Falsches Passwort.");
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Benutzername {0} nicht gefunden.", benutzername);
                    throw new UserLoginException("Benutzername nicht gefunden.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Fehler bei der Verbindung zur Datenbank.", e);
            throw new UserLoginException("Fehler bei der Verbindung zur Datenbank.");
        }
    }
}
