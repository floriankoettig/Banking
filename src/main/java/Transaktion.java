public class Transaktion {
    //protected int idTransaktion;
    protected int kontonummer;
    protected String empfaengerKontonummer;

    protected int betrag;
    protected String verwendungszweck;

    public Transaktion(int kontonummer, String empfaengerKontonummer, int betrag, String verwendungszweck) {
        this.kontonummer = kontonummer;
        this.empfaengerKontonummer = empfaengerKontonummer;
        this.betrag = betrag;
        this.verwendungszweck = verwendungszweck;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getEmpfaengerKontonummer() {
        return empfaengerKontonummer;
    }

    public void setEmpfaengerKontonummer(String empfaengerKontonummer) {
        this.empfaengerKontonummer = empfaengerKontonummer;
    }

    public int getBetrag() {
        return betrag;
    }

    public void setBetrag(int betrag) {
        this.betrag = betrag;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public void setVerwendungszweck(String verwendungszweck) {
        this.verwendungszweck = verwendungszweck;
    }
}