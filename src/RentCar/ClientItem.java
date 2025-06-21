package RentCar;

public class ClientItem {
    private int numerKlienta;
    private String imie;
    private String nazwisko;

    public ClientItem(int numerKlienta, String imie, String nazwisko) {
        this.numerKlienta = numerKlienta;
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public int getNumerKlienta() {
        return numerKlienta;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko + " (" + numerKlienta + ")";
    }
}