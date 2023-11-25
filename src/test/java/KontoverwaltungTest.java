import static org.junit.Assert.*;
import org.junit.Test;

public class KontoverwaltungTest {
    @Test
    public void testIsKontonummerValid() {
        Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
        assertTrue(kontoverwaltung.isKontonummerValid("12345678"));
        assertFalse(kontoverwaltung.isKontonummerValid("1234"));
        assertFalse(kontoverwaltung.isKontonummerValid("abcd1234"));
        //weitere testfälle
    }
}
