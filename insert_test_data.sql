INSERT INTO "Benutzer" (benutzername, vorname, nachname, password)
VALUES
    ('johndoe', 'John', 'Doe', 'pass123'),
    ('janedoe', 'Jane', 'Doe', 'pass456');

INSERT INTO "Konto" (kontonummer, kontostand, "idBenutzer")
VALUES
    ('12345678', 1000.00, 'ca12f7b8-2525-423d-8d75-7c63e6c4e6f4'),
    ('87654321', 500.00, '4e4c536a-a451-4153-962c-88c2acecc9c5');

INSERT INTO "Transaktion" (kontonummer, "empfaengerKontonummer", verwendungszweck, betrag)
VALUES
    ('12345678', '87654321', 'Miete', 700.00);
