package ShowCustomers;

public class Klienci {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private int number;

    public Klienci(int id, String name, String surname, String email, String phone, int number) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.number = number;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public int getNumber() { return number; }
}
