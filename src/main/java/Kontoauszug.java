import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
class Kontoauszug {
    private String transaktionsdatum;
    private String empfaengerSender;
    private String verwendungszweck;
    private double betrag;
    private double neuerKontostand;

    public Kontoauszug(String transaktionsdatum, String empfaengerSender, String verwendungszweck, double betrag, double neuerKontostand) {
        this.transaktionsdatum = transaktionsdatum;
        this.empfaengerSender = empfaengerSender;
        this.verwendungszweck = verwendungszweck;
        this.betrag = betrag;
        this.neuerKontostand = neuerKontostand;
    }

    public String getTransaktionsdatum() {
        return transaktionsdatum;
    }

    public String getEmpfaengerSender() {
        return empfaengerSender;
    }

    public String getVerwendungszweck() {
        return verwendungszweck;
    }

    public double getBetrag() {
        return betrag;
    }

    public double getNeuerKontostand() {
        return neuerKontostand;
    }

    public String[] toCSV() {
        return new String[]{transaktionsdatum, empfaengerSender, verwendungszweck, String.valueOf(betrag), String.valueOf(neuerKontostand)};
    }
    private static void exportiereKontoauszug(Kontoauszug kontoauszug) {
        // Geben Sie den Dateipfad für die CSV-Datei an
        String csvFilePath = "pfad/zum/kontoauszug_" + kontoauszug.getTransaktionsdatum() + ".csv";

        try {
            // Erstellen Sie einen BufferedWriter für die CSV-Datei
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));

            // Schreiben Sie den Header in die CSV-Datei
            writeLine(writer, new String[]{"Alter Kontostand"});
            writeLine(writer, new String[]{"Transaktionsdatum", "Empfänger/Sender", "Verwendungszweck", "Betrag", "Neuer Kontostand"});

            // Schreiben Sie die Transaktionsdaten in die CSV-Datei
            writeLine(writer, kontoauszug.toCSV());

            // Schließen Sie den BufferedWriter
            writer.close();

            System.out.println("Kontoauszug erfolgreich erstellt: " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLine(BufferedWriter writer, String[] values) throws IOException {
        // Erstellen Sie eine Zeile, indem Sie die Werte durch Semikolons trennen
        String line = String.join(";", values);

        // Schreiben Sie die Zeile in die CSV-Datei
        writer.write(line);

        // Fügen Sie einen Zeilenumbruch hinzu
        writer.newLine();
    }
}


