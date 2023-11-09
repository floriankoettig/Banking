import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Benutzerverwaltung {
    private static Map<String, String> benutzerdaten = new HashMap<>();
    static Connection conn = DbConnection.getConnection();

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
                System.out.print("Vorname eingeben: ");
                String vorname = scanner.nextLine();
                System.out.print("Nachname eingeben: ");
                String nachname = scanner.nextLine();
                System.out.print("Passwort eingeben: ");
                String passwort = scanner.nextLine();
                benutzerdaten.put(benutzername, passwort);
                Benutzerverwaltung.registriereBenutzer(benutzername, vorname, nachname, passwort);
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
    private static void registriereBenutzer(String benutzername, String vorname, String nachname, String passwort) {
        try {
            String insertQuery = """
            INSERT INTO "Benutzer" ("benutzername", "vorname", "nachname", "passwort")
            VALUES (?, ?, ?, ?)
            """;
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

            preparedStatement.setString(1, benutzername);
            preparedStatement.setString(2, vorname);
            preparedStatement.setString(3, nachname);
            preparedStatement.setString(4, passwort);

            preparedStatement.executeUpdate();
            preparedStatement.close();

            System.out.println("Benutzer erfolgreich registriert.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Registrieren des Benutzers.");
        }
    }
}
