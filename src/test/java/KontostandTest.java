import exceptions.AccountNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class KontostandTest {

        @Test
        public void kontostandTest(){
            Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
            try (Connection con = DbConnection.getConnection() ){
                kontoverwaltung.kontostandAbfragen( 12345678);
                double result = kontoverwaltung.kontostandAbfragen(12345678);
                Assertions.assertEquals(1345.00, result);
            } catch (SQLException | AccountNotFoundException e) {
                throw new RuntimeException(e);
            } finally {

            }
        }
    }

