import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Anmeldung {
    private static Map<String, String> benutzerdaten = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Registrieren");
            System.out.println("2. Anmelden");
            System.out.println("3. Beenden");
            System.out.print("Wähle eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Benutzername eingeben: ");
                String benutzername = scanner.nextLine();
                System.out.print("Passwort eingeben: ");
                String passwort = scanner.nextLine();
                benutzerdaten.put(benutzername, passwort);
                System.out.println("Benutzer erfolgreich registriert.");
            } else if (option == 2) {
                System.out.print("Benutzername eingeben: ");
                String benutzername = scanner.nextLine();
                System.out.print("Passwort eingeben: ");
                String passwort = scanner.nextLine();

                if (benutzerdaten.containsKey(benutzername) && benutzerdaten.get(benutzername).equals(passwort)) {
                    System.out.println("Anmeldung erfolgreich!");
                } else {
                    System.out.println("Falsche Anmeldedaten.");
                }
            } else if (option == 3) {
                break;
            }
        }
    }
}
