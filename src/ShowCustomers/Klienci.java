package ShowCustomers;

public class Klienci {
    private int id;
    private String imie;
    private String nazwisko;
    private String email;
    private String telefon;
    private int numer;

    public Klienci(int id, String imie, String nazwisko, String email, String telefon, int numer) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.telefon = telefon;
        this.numer = numer;
    }

    public int getId() { return id; }
    public String getImie() { return imie; }
    public String getNazwisko() { return nazwisko; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }
    public int getNumer() { return numer; }
}
