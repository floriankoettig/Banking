import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
            "benutzername" varchar(255) not null,
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

        //94cbfbb01a39316c0ff7f0bfe6499a506437b5bf

        }
    }


