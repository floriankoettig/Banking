import exceptions.AccountNotFoundException;
import exceptions.DepositException;
import exceptions.InvalidAmountException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.sql.Connection;
import java.sql.SQLException;

public class KontoverwaltungTest {
    @Test
    public void betragEinzahlenTest() {
        try (Connection connection = DbConnection.getConnection()) {
            Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
            kontoverwaltung.einzahlen(connection, 12345678, 30);
            double result = kontoverwaltung.kontostandAbfragen(12345678);
            Assertions.assertEquals(1280.00, result);
        } catch (SQLException e) {
            System.out.println("mies gelaufen");
        } catch (InvalidAmountException | DepositException | AccountNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    /*@Test
    public void testIsKontonummerValid() {
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        assertTrue(kontoverwaltung.isKontonummerValid("12345678"));
        assertFalse(kontoverwaltung.isKontonummerValid("1234"));
        assertFalse(kontoverwaltung.isKontonummerValid("abcd1234"));
        //weitere testfälle
    }

    @Test
    public void erstellenTest() {
        assertDoesNotThrow(() -> new Kontoverwaltung().erstelleKonto(UUID.randomUUID()));
    }
*/

}


