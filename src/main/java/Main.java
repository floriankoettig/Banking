import exceptions.UserLoginException;
import exceptions.UserRegistrationException;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UserRegistrationException, UserLoginException {
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
                            int kontonummer = kontoverwaltung.ermittleKontonummer(benutzername);
                            System.out.println("Kontonummer: " + kontonummer);

                            boolean angemeldet = true;
                            while (angemeldet) {
                                System.out.println("1. Kontostand abfragen");
                                System.out.println("2. Einzahlen");
                                System.out.println("3. Abheben");
                                System.out.println("4. Abmelden");
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
                                        try {
                                            kontoverwaltung.einzahlen(kontonummer, betragEinzahlen);
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
                                        try {
                                            kontoverwaltung.abheben(kontonummer, betragAbheben);
                                            System.out.println("Betrag erfolgreich abgehoben.");
                                        } catch (SQLException e) {
                                            System.out.println("Fehler bei der Abhebung: " + e.getMessage());
                                        }
                                        break;
                                    case 4: //abmelden
                                        angemeldet = false;
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
                    } catch (SQLException e) {
                        System.out.println("Datenbankfehler: " + e.getMessage());
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