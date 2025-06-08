package ShowCars;

public class Samochod {
    private int id;
    private String marka;
    private String model;
    private int rokProdukcji;
    private String nrRejestracyjny;
    private boolean dostepny;
    private String zdjecie;

    public Samochod(int id, String marka, String model, int rokProdukcji, String nrRejestracyjny, boolean dostepny, String zdjecie) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.rokProdukcji = rokProdukcji;
        this.nrRejestracyjny = nrRejestracyjny;
        this.dostepny = dostepny;
        this.zdjecie = zdjecie;
    }

    public int getId() { return id; }
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public int getRokProdukcji() { return rokProdukcji; }
    public String getNrRejestracyjny() { return nrRejestracyjny; }
    public boolean isDostepny() { return dostepny; }
    public String getZdjecie() { return zdjecie; }
}