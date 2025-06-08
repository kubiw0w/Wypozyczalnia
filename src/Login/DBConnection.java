package Login;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String username;
    private static String password;

    public static void setCredentials(String user, String pass) {
        username = user;
        password = pass;
    }

    public static Connection getConnection() {
        String url = "jdbc:oracle:thin:@//localhost:1521/orclpdb";

        try {
            if (username == null || password == null) {
                System.err.println("Brak ustawionych danych logowania.");
                return null;
            }
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.err.println("Błąd przy tworzeniu połączenia:");
            e.printStackTrace();
            return null;
        }
    }
}
