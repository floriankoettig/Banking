import java.util.UUID;

public class Konto {
    protected int kontonummer;
    protected double kontostand;
    protected UUID idBenutzer;

    public Konto(int kontonummer, double kontostand, UUID idBenutzer) {
        this.kontonummer = kontonummer;
        this.kontostand = kontostand;
        this.idBenutzer = idBenutzer;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    public UUID getIdBenutzer() {
        return idBenutzer;
    }

    public void setIdBenutzer(UUID idBenutzer) {
        this.idBenutzer = idBenutzer;
    }
}