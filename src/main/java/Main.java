import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
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
    }
}
