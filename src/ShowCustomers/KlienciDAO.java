package ShowCustomers;

import Login.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class KlienciDAO {
    public static List<Klienci> getAllKlienci() throws SQLException {
        List<Klienci> klienci = new ArrayList<>();

        String query = "SELECT * FROM KLIENCI";
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            throw new SQLException("Brak połączenia z bazą danych.");
        }

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Klienci k = new Klienci(
                        rs.getInt("ID_KLIENTA"),
                        rs.getString("IMIE"),
                        rs.getString("NAZWISKO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFON"),
                        rs.getInt("NUMER_KLIENTA")
                );
                klienci.add(k);
            }
        }

        return klienci;
    }
}
