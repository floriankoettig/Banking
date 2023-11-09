public class Transaktion {
    //protected int transaktionID;
    protected int kontonummer;
    protected String empfaengerIBAN;
    protected String verwendungszweck;
    protected int betrag;

    public Transaktion(int kontonummer, String empfaengerIBAN, String verwendungszweck, int betrag) {
        this.kontonummer = kontonummer;
        this.empfaengerIBAN = empfaengerIBAN;
        this.verwendungszweck = verwendungszweck;
        this.betrag = betrag;
    }

    public Transaktion(int kontonummer, String empfaengerIBAN, int betrag) {
        this.kontonummer = kontonummer;
        this.empfaengerIBAN = empfaengerIBAN;
        this.betrag = betrag;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getEmpfaengerIBAN() {
        return empfaengerIBAN;
    }

    public void setEmpfaengerIBAN(String empfaengerIBAN) {
        this.empfaengerIBAN = empfaengerIBAN;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }

    public int getBetrag() {
        return betrag;
    }

    public void setBetrag(int betrag) {
        this.betrag = betrag;
    }
}
