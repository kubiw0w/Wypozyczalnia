package ShowCars;

import Login.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SamochodDAO {
    public static List<Samochod> getAllCars() throws SQLException {
        List<Samochod> cars = new ArrayList<>();

        String query = "SELECT * FROM SAMOCHODY";
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Brak połączenia z bazą danych.");
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Samochod s = new Samochod(
                        rs.getInt("ID_SAMOCHODU"),
                        rs.getString("MARKA"),
                        rs.getString("MODEL"),
                        rs.getInt("ROK_PRODUKCJI"),
                        rs.getString("NR_REJESTRACYJNY"),
                        rs.getString("DOSTĘPNY").equalsIgnoreCase("T"),
                        rs.getString("ZDJECIE")
                );
                cars.add(s);
            }
        }

        return cars;
    }
}
