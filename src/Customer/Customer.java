package Customer;

/**
 * Klass som representerar en kund i webbshopen
 * Används för att hantera kunddata mellan databasen och applikationen
 */
public class Customer {

    // Privata fält för att uppnå inkapsling
    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;


    /**
     * Konstruktor för att skapa en ny Customer.Customer
     * Tar emot all nödvändig information för en kund
     *
     */
    public Customer(int customerId, String name, String email, String phone, String address, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    // Getters och setters för alla fält

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * toString-metod för att få en läsbar representation av kunden
     * Användbar vid utskrift eller debugging
     */

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}