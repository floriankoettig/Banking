INSERT INTO "Benutzer" (benutzerkennung, benutzername, vorname, nachname, passwort)
VALUES
    ('JD','johndoe', 'John', 'Doe', 'pass123'),
    ('JD1', 'janedoe', 'Jane', 'Doe', 'pass456');

INSERT INTO "Konto" (kontonummer, kontostand, "idBenutzer")
VALUES
    ('12345678', 1000.00, 'b069b6c4-964b-4acd-b300-3f50b9e466b5'),
    ('87654321', 500.00, '553f85a8-3900-44ca-83ad-1c97bf67592d');

INSERT INTO "Transaktion" (kontonummer, "empfaengerKontonummer", verwendungszweck, betrag)
VALUES
    ('12345678', '87654321', 'Miete', 700.00);
