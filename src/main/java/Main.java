import exceptions.AccountNotFoundException;
import exceptions.UserLoginException;
import exceptions.UserRegistrationException;
import java.io.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws UserRegistrationException, UserLoginException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Benutzerverwaltung benutzerverwaltung = new Benutzerverwaltung();
        System.out.println("Willkommen bei der Bank!");

        while (true) {
            System.out.println("1. Registrieren");
            System.out.println("2. Anmelden");
            System.out.println("3. Beenden");
            System.out.print("Wähle eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1: //registrierung
                    try {
                        System.out.print("Benutzername eingeben: ");
                        String benutzername = scanner.nextLine();
                        System.out.print("Vorname eingeben: ");
                        String vorname = scanner.nextLine();
                        System.out.print("Nachname eingeben: ");
                        String nachname = scanner.nextLine();
                        System.out.print("Passwort eingeben: ");
                        String passwort = scanner.nextLine();

                        benutzerverwaltung.registrieren(benutzername, vorname, nachname, passwort);
                    } catch ( UserRegistrationException e) {
                        System.out.println("Fehler bei der Registrierung: " + e.getMessage());
                    }
                    break;
                case 2: //anmeldung
                    try {
                        System.out.print("Benutzername eingeben: ");
                        String benutzername = scanner.nextLine();
                        System.out.print("Passwort eingeben: ");
                        String passwort = scanner.nextLine();

                        if (benutzerverwaltung.anmelden(benutzername, passwort)) {
                            System.out.println("Anmeldung erfolgreich!");

                            Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
                            int kontonummer = -1;
                            try {
                                kontonummer = kontoverwaltung.ermittleKontonummer(benutzername);
                                System.out.println("Kontonummer: " + kontonummer);
                            } catch (AccountNotFoundException e) {
                                System.out.println("Fehler: " + e.getMessage());
                            }

                            boolean isLoggedIn = true;
                            while (isLoggedIn) {
                                System.out.println("1. Kontostand abfragen");
                                System.out.println("2. Einzahlen");
                                System.out.println("3. Abheben");
                                System.out.println("4. Überweisen");
                                System.out.println("5. Kontoauszug drucken");
                                System.out.println("6. Massenüberweisung tätigen");
                                System.out.println("7. Abmelden");
                                System.out.print("Wähle eine Option: ");
                                int optionAnmeldung = scanner.nextInt();
                                scanner.nextLine();

                                switch (optionAnmeldung) {
                                    case 1: //kontostand
                                        double kontostand = kontoverwaltung.kontostandAbfragen(kontonummer);
                                        System.out.println("Aktueller Kontostand: " + kontostand);
                                        break;
                                    case 2: //einzahlen
                                        //todo: implemtierung für mehrere konten
                                        /*System.out.println("Auf welches Konto möchten Sie einzahlen?");
                                        System.out.print("Kontonummer eingeben: ");
                                        kontonummer = scanner.nextInt();*/
                                        System.out.print("Einzahlungsbetrag eingeben: ");
                                        double betragEinzahlen = scanner.nextDouble();
                                        try (Connection conn = DbConnection.getConnection()){
                                            kontoverwaltung.einzahlen(conn, kontonummer, betragEinzahlen);
                                            System.out.println("Betrag erfolgreich eingezahlt.");
                                        } catch (SQLException e) {
                                            System.out.println("Fehler bei der Einzahlung: " + e.getMessage());
                                        }
                                        break;
                                    case 3: //abheben
                                        //todo: implemtierung für mehrere konten
                                        /*System.out.println("Von welchem Konto möchten Sie abheben?");
                                        System.out.print("Kontonummer eingeben: ");
                                        kontonummer = scanner.nextInt();*/
                                        System.out.print("Abhebungsbetrag eingeben: ");
                                        double betragAbheben = scanner.nextDouble();
                                        try (Connection conn = DbConnection.getConnection()){
                                            kontoverwaltung.abheben(conn, kontonummer, betragAbheben);
                                            System.out.println("Betrag erfolgreich abgehoben.");
                                        } catch (SQLException e) {
                                            System.out.println("Fehler bei der Abhebung: " + e.getMessage());
                                        }
                                        break;
                                    case 4: //überweisen
                                        //todo: implemtierung für mehrere konten
                                        /*System.out.println("Von welchem Konto möchten Sie überweisen?");
                                        System.out.print("Kontonummer eingeben: ");
                                        kontonummer = scanner.nextInt();*/
                                        System.out.print("Kontonummer des Empfängers: ");
                                        String empfaengerKontonummer = scanner.nextLine();
                                        empfaengerKontonummer = empfaengerKontonummer.trim();
                                        if (!kontoverwaltung.isKontonummerValid(empfaengerKontonummer)) {
                                            System.out.println("Ungültige Kontonummer des Empfängers. Bitte gültige 8-stellige Kontonummer eingeben.");
                                            break;
                                        }
                                        System.out.print("Überweisungsbetrag eingeben: ");
                                        double betragUeberweisen = scanner.nextDouble();
                                        scanner.nextLine();
                                        System.out.print("Verwendungszweck eingeben: ");
                                        String verwendungszweck = scanner.nextLine();
                                        if (!kontoverwaltung.isVerwendungszweckValid(verwendungszweck)) {
                                            System.out.println("Ungültiger Verwendungszweck. Bitte nur Buchstaben, Zahlen und Leerzeichen verwenden.");
                                            break;
                                        }
                                        try (Connection conn = DbConnection.getConnection()) {
                                            kontoverwaltung.ueberweisen(conn, kontonummer, Integer.parseInt(empfaengerKontonummer), betragUeberweisen, verwendungszweck);
                                            System.out.println("Betrag erfolgreich überwiesen.");
                                        } catch (SQLException e) {
                                            System.out.println("Fehler bei der Überweisung: " + e.getMessage());
                                        }
                                        break;
                                    case 5: //kontoauszug
                                        try (Connection conn = DbConnection.getConnection()) {
                                            Kontoverwaltung.exportTransactionsByAccountNumber(kontonummer);
                                        } catch (SQLException e) {
                                            System.out.println("Fehler bei der Überweisung: " + e.getMessage());
                                        }
                                        break;
                                    case 6: //Massenüberweisung
                                        System.out.println("Bitte legen Sie die Datei in den Ablage Ordner und geben Sie den Dateinamen an.");
                                        System.out.print("Dateiname: ");
                                        String fileName = scanner.nextLine();
                                        String filePath = "C:\\Users\\U0125812\\Desktop\\" +fileName; // Passe dies entsprechend an

                                        try (Scanner scanner1 = new Scanner(new File(filePath))) {
                                                // Überspringe die Header-Zeile
                                                if (scanner1.hasNextLine()) {
                                                    scanner1.nextLine();
                                                }

                                                // Durch die CSV-Datei iterieren
                                                while (scanner1.hasNextLine()) {
                                                    String line = scanner1.nextLine();
                                                    String[] parts = line.split(";");

                                                    // Validiere und verarbeite die Daten
                                                    if (Kontoverwaltung.isUeberweisungValid(parts)) {
                                                        Kontoverwaltung.processTransaction(parts, kontonummer);
                                                    } else {
                                                        System.out.println("Ungültige Transaktion: " + line);
                                                    }
                                                }

                                                System.out.println("Massenüberweisungen erfolgreich verarbeitet.");
                                            } catch(FileNotFoundException e) {
                                            System.out.println("Datei wurde nicht gefunden.");
                                        }
                                    break;
                                    case 7: //abmelden
                                        isLoggedIn = false;
                                        break;
                                    default:
                                        System.out.println("Ungültige Option.");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Falsche Anmeldedaten.");
                        }                        
                    } catch (UserLoginException e) {
                        System.out.println("Fehler bei der Anmeldung: " + e.getMessage());
                    }
                    break;
                case 3: //beenden
                    System.out.println("Anwendung wird beendet.");
                    return;
                default:
                    System.out.println("Ungültige Option.");
                    break;
            }
        }
    }
}