import exceptions.UserLoginException;
import exceptions.UserRegistrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BenutzerverwaltungTest {

    private final Benutzerverwaltung benutzerverwaltung = new Benutzerverwaltung();

    @BeforeEach
    public void setup() {
        // Testdatenbank erstellen (fehlt offensichtlich)
    }

    @AfterEach
    public void teardown() {
        // Testdatenbank reseten nach Tests bspw.
    }

    @Test
    public void testRegistrieren() {
        // Generate unique test data for each run
        String benutzername = "testUser" + UUID.randomUUID().toString().substring(0, 8);
        String vorname = "Test";
        String nachname = "User";
        String passwort = "testPassword";

        try {
            benutzerverwaltung.registrieren(benutzername, vorname, nachname, passwort);
        } catch (UserRegistrationException e) {
            fail("User registration should not throw an exception: " + e.getMessage());
        }

        // Check ob Regristierung erfolgreich war
        assertTrue(isUserRegistered(benutzername));
    }

    @Test
    public void testAnmelden() {
        // Generiere neue Testdaten pro durchlauf
        String benutzername = "testUser" + UUID.randomUUID().toString().substring(0, 8);
        String passwort = "testPassword";

        // Regristrierung von test user
        try {
            benutzerverwaltung.registrieren(benutzername, "Test", "User", passwort);
        } catch (UserRegistrationException e) {
            fail("User registration should not throw an exception: " + e.getMessage());
        }

        // login mit richtigem Passwort
        try {
            assertTrue(benutzerverwaltung.anmelden(benutzername, passwort));
        } catch (UserLoginException e) {
            fail("User login should not throw an exception: " + e.getMessage());
        }

        // login mit falschem
        String wrongPassword = "wrongPassword";
        assertThrows(UserLoginException.class, () -> benutzerverwaltung.anmelden(benutzername, wrongPassword));
    }

    private boolean isUserRegistered(String benutzername) {
        // logik...
        return true; // Placeholder, replace with actual logic
    }
}
