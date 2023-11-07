public class Konto {
    protected int kontonummer;
    protected double kontostand;
    public Konto(int kontonummer, double kontostand) {
        this.kontonummer = kontonummer;
        this.kontostand = kontostand;
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
}
