public class Benutzer {
    protected int idBenutzer;
    protected String vorname;
    protected String nachname;
    protected String password;
    protected int kontonummer;

    public Benutzer(int idBenutzer, String vorname, String nachname, String password, int kontonummer) {
        this.idBenutzer = idBenutzer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.password = password;
        this.kontonummer = kontonummer;
    }

    public int getIdBenutzer() {
        return idBenutzer;
    }

    public void setIdBenutzer(int idBenutzer) {
        this.idBenutzer = idBenutzer;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer) {
        this.kontonummer = kontonummer;
    }
}
