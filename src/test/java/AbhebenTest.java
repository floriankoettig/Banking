import exceptions.AccountNotFoundException;
import exceptions.InvalidAmountException;
import exceptions.WithdrawalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class AbhebenTest {

    @Test
    public void AbhebenTest() {
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        try (Connection con = DbConnection.getConnection()) {
            kontoverwaltung.abheben(con, 12345678, 30);
            double result = kontoverwaltung.kontostandAbfragen(12345678);
            Assertions.assertEquals(1315.00, result);
        } catch (SQLException | AccountNotFoundException | InvalidAmountException |
                 WithdrawalException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}
