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
                case 1: //Registrierung
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
                case 2: //Anmeldung
                    try {
                        System.out.print("Benutzername eingeben: ");
                        String benutzername = scanner.nextLine();
                        System.out.print("Passwort eingeben: ");
                        String passwort = scanner.nextLine();

                        if (benutzerverwaltung.anmelden(benutzername, passwort)) {
                            Kontoverwaltung kontoverwaltung = new Kontoverwaltung();
                            int kontonummer = kontoverwaltung.ermittleKontonummer(benutzername);
                            double kontostand = kontoverwaltung.kontostandAbfragen(kontonummer);
                            System.out.println("Kontonummer: " + kontonummer);
                            System.out.println("Aktueller Kontostand: " + kontostand);
                        } else {
                            System.out.println("Falsche Anmeldedaten.");
                        }                        
                    } catch (UserLoginException e) {
                        System.out.println("Fehler bei der Anmeldung: " + e.getMessage());
                    } catch (SQLException e) {
                        System.out.println("Datenbankfehler: " + e.getMessage());
                    }
                    break;
                case 3: //Beenden
                    System.out.println("Anwendung wird beendet.");
                    return;
                default:
                    System.out.println("Ungültige Option.");
                    break;
            }
        }
    }
}