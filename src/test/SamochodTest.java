package test;

import ShowCars.Samochod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SamochodTest {

    @Test
    public void SamochodTest() {

        int id = 101;
        String brand = "Toyota";
        String model = "Corolla";
        int year = 2020;
        String registrationNr = "KR12345";
        boolean available = true;
        String image = "/images/toyota_corolla.jpg";

        Samochod car = new Samochod(id, brand, model, year, registrationNr, available, image);

        assertEquals(id, car.getId());
        assertEquals(brand, car.getBrand());
        assertEquals(model, car.getModel());
        assertEquals(year, car.getYear());
        assertEquals(registrationNr, car.getRegistrationNr());
        assertTrue(car.isAvailable());
        assertEquals(image, car.getImage());
    }
}

