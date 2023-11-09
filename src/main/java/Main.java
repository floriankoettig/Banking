import java.sql.SQLException;
import java.util.Scanner;

/*
!!!
erstellt mal bitte einen neuen branch, wenn ihr was pusht
!!!
 */

public class Main {
    public static void main(String[] args) throws SQLException {
        //Datenbankverbindung
        var conn = DbConnection.getConnection();
        var statement = conn.createStatement();

        statement.execute("""
            CREATE TABLE IF NOT EXISTS "Konto" (
            "kontonummer" serial PRIMARY KEY,
            "kontostand" double
            );
            CREATE TABLE IF NOT EXISTS "Benutzer" (
            "idBenutzer" UNIQUEIDENTIFIER DEFAULT NEWID() PRIMARY KEY,
            "vorname" varchar(255) not null,
            "nachname" varchar(255) not null,
            "password" varchar(255) not null,
            "kontonummer" int REFERENCES "Konto"
            );
            CREATE TABLE IF NOT EXISTS "Transaktion" (
            "kontonummer" int REFERENCES "Konto",
            "empfaengerIBAN" char not null,
            "verwendungszweck" varchar(255),
            "betrag" int not null
            )
            """);

        //Login Eingabe
        Scanner scanner = new Scanner(System.in);
        System.out.print("Benutzername eingeben: ");
        String benutzername = scanner.nextLine();
        System.out.print("Passwort eingeben: ");
        String passwort = scanner.nextLine();

        //Prüfung
        //94cbfbb01a39316c0ff7f0bfe6499a506437b5bf
    }
}
