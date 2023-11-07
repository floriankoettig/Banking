import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Datenbankverbindung
        var conn = DbConnection.getConnection();
        var statement = conn.createStatement();

        statement.execute("""
            CREATE TABLE IF NOT EXISTS "Konto" (
            "kontonummer" serial PRIMARY KEY
            );
            CREATE TABLE IF NOT EXISTS "Benutzer" (
            "idBenutzer" UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
            "vorname" varchar(255) not null,
            "nachname" varchar(255) not null,
            "password" varchar (255) not null,
            "kontonummer" int REFERENCES "Konto"
            );
            """);
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
