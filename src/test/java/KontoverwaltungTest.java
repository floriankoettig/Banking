import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

import java.util.UUID;

public class KontoverwaltungTest {
    @Test
    public void testIsKontonummerValid() {
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        assertTrue(kontoverwaltung.isKontonummerValid("12345678"));
        assertFalse(kontoverwaltung.isKontonummerValid("1234"));
        assertFalse(kontoverwaltung.isKontonummerValid("abcd1234"));
        //weitere testfälle
    }

    @Test
    public void erstellenTest() {
        assertDoesNotThrow(() -> new Kontoverwaltung().erstellen(UUID.randomUUID()));
    }
}
