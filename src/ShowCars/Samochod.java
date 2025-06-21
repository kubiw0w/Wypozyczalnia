package ShowCars;

public class Samochod {
    private int id;
    private String brand;
    private String model;
    private int year;
    private String registrationNr;
    private boolean available;
    private String image;

    public Samochod(int id, String brand, String model, int year, String registrationNr, boolean available, String image) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registrationNr = registrationNr;
        this.available = available;
        this.image = image;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getRegistrationNr() { return registrationNr; }
    public boolean isAvailable() { return available; }
    public String getImage() { return image; }
}