package RentCar;

public class CarItem {
    private String nrRejestracyjny;
    private String marka;
    private String model;

    public CarItem(String nrRejestracyjny, String marka, String model) {
        this.nrRejestracyjny = nrRejestracyjny;
        this.marka = marka;
        this.model = model;
    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny;
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return marka + " " + model + " (" + nrRejestracyjny + ")";
    }
}
