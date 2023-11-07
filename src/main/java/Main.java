import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Datenbankverbindung
        var conn = DbConnection.getConnection();
        var statement = conn.createStatement();

        // LogIn
        // Eingabe
        Scanner scanner = new Scanner(System.in);
        System.out.println("Benutzername eingeben:");
        String trybenutzername = scanner.nextLine();
        System.out.println("Passwort eingeben:");
        String trypasswort = scanner.nextLine();

        //Prüfung
    }
}
