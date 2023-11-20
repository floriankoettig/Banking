import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Datenbankverbindung
        var conn = DbConnection.getConnection();
        var statement = conn.createStatement();

        statement.execute("""
        CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
        CREATE TABLE IF NOT EXISTS "Benutzer" (
            "idBenutzer" UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
            "benutzername" VARCHAR(255) UNIQUE NOT NULL,
            "vorname" VARCHAR(255) NOT NULL,
            "nachname" VARCHAR(255) NOT NULL,
            "password" VARCHAR(255) NOT NULL,
            "kontonummer" INT UNIQUE REFERENCES "Konto"
            );
        CREATE TABLE IF NOT EXISTS "Konto" (
            "kontonummer" SERIAL PRIMARY KEY,
            "kontostand" DECIMAL(15, 2) DEFAULT 0.00,
            "idBenutzer" UUID REFERENCES "Benutzer"
            );
        CREATE TABLE IF NOT EXISTS "Transaktion" (
            "idTransaktion" SERIAL PRIMARY KEY,
            "kontonummer" INT REFERENCES "Konto",
            "empfaengerKontonummer" VARCHAR(34) NOT NULL,
            "verwendungszweck" VARCHAR(255),
            "betrag" DECIMAL(15,2) NOT NULL,
            "timestamp" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        //Login
        Scanner scanner = new Scanner(System.in);
        System.out.print("Benutzername eingeben: ");
        String benutzername = scanner.nextLine();
        System.out.print("Passwort eingeben: ");
        String passwort = scanner.nextLine();
    }
}
