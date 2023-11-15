import java.util.Scanner;

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

    // Actions

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Kontostand abfragen");
        System.out.println("2. Kontoauszug exportieren");
        System.out.println("3. Überweisung tätigen");
        System.out.println("4. Massenüberweisung per CSV");
        System.out.print("Was möchtest du tun?");
    int auswahl = scanner.nextInt();
        scanner.nextLine();

        switch (auswahl) {
        case 1:
            // Kontostand abfragen
            System.out.println("Kontostand: ");
            break;
        case 2:
            // Kontoauszug exportieren
            System.out.println("Eine neue Datei wurde erstellt: ");
            break;
        case 3:
            // Überweisung tätigen
            System.out.println("Bitte geben sie folgende Daten an:");
            System.out.print("Empfänger IBAN:");
            String empfängerIBAN = scanner.nextLine();
            System.out.print("Verwendungszweck:");
            String verwendungszweck = scanner.nextLine();
            System.out.print("Betrag:");
            String betrag = scanner.nextLine();

            System.out.println("Überweisung bestätigen");
            System.out.println("Empfänger IBAN: " + empfängerIBAN);
            System.out.println("Verwendungszweck: " + verwendungszweck);
            System.out.println("Betrag: " + betrag);
            System.out.println("1. Ja");
            System.out.println("2. Nein");
            int bestätigung = scanner.nextInt();
            switch (bestätigung) {
                case 1:
                    System.out.println("Überweisung erfolgreich");
                    break;

                case 2:
                    System.out.println("Überweisung Abgebrochen");
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte 1 oder 2 eingeben.");
            }

            break;
        case 4:
            System.out.println("Massenüberweisung per CSV tätigen");
            break;
        default:
            System.out.println("Ungültige Auswahl. Bitte 1, 2, 3 oder 4 eingeben.");
}}    }

