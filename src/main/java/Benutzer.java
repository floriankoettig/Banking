public class Benutzer {
    protected int idBenutzer;
    protected int benutzername;
    protected String vorname;
    protected String nachname;
    protected String passwort;
    protected int kontonummer;

    public Benutzer(int idBenutzer, int benutzername, String vorname, String nachname, String passwort, int kontonummer) {
        this.idBenutzer = idBenutzer;
        this.benutzername = benutzername;
        this.vorname = vorname;
        this.nachname = nachname;
        this.passwort = passwort;
        this.kontonummer = kontonummer;
    }

    public int getIdBenutzer() {
        return idBenutzer;
    }

    public void setIdBenutzer(int idBenutzer) {
        this.idBenutzer = idBenutzer;
    }

    public int getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(int benutzername) {
        this.benutzername = benutzername;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }
}