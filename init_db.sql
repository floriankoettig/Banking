CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS "Benutzer" (
    "idBenutzer" UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    "benutzerkennung" VARCHAR(255) UNIQUE NOT NULL,
    "benutzername" VARCHAR(255) UNIQUE NOT NULL,
    "vorname" VARCHAR(255) NOT NULL,
    "nachname" VARCHAR(255) NOT NULL,
    "passwort" VARCHAR(255) NOT NULL
--   keine FK reference zu "Konto", da 1:n besser
--  "kontonummer" CHAR(8) REFERENCES "Konto"
);
CREATE TABLE IF NOT EXISTS "Konto" (
    "kontonummer" CHAR(8) PRIMARY KEY,
    "kontostand" DECIMAL(15, 2) DEFAULT 0.00,
    "idBenutzer" UUID REFERENCES "Benutzer"
);
CREATE TABLE IF NOT EXISTS "Transaktion" (
    "idTransaktion" SERIAL PRIMARY KEY,
    "kontonummer" CHAR(8) REFERENCES "Konto",
    "empfaengerKontonummer" VARCHAR(34) NOT NULL,
    "betrag" DECIMAL(15,2) NOT NULL,
    "verwendungszweck" VARCHAR(255),
    "timestamp" TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);