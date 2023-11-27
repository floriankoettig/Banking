import exceptions.AccountNotFoundException;
import exceptions.DepositException;
import exceptions.InvalidAmountException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.SQLException;

public class EinzahlenTest {

    @Test
    public void einzahlenTest(){
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        try (Connection con = DbConnection.getConnection() ){
            kontoverwaltung.einzahlen(con, 12345678, 30);
            double result = kontoverwaltung.kontostandAbfragen(12345678);
            Assertions.assertEquals(1345.00, result);
        } catch (SQLException | AccountNotFoundException | InvalidAmountException | DepositException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }
}

