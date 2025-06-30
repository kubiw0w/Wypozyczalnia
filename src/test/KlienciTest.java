package test;
import ShowCustomers.Klienci;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KlienciTest {

    @Test
    public void KlienciTest() {
        int id = 1;
        String name = "Jan";
        String surname = "Kowalski";
        String email = "jan.kowalski@example.com";
        String phone = "123456789";
        int number = 5;

        Klienci klient = new Klienci(id, name, surname, email, phone, number);

        assertEquals(id, klient.getId());
        assertEquals(name, klient.getName());
        assertEquals(surname, klient.getSurname());
        assertEquals(email, klient.getEmail());
        assertEquals(phone, klient.getPhone());
        assertEquals(number, klient.getNumber());
    }
}

