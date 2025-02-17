package Customer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Service-klass för kundhantering
 * Innehåller affärslogik mellan controller och repository
 */
public class CustomerService {

    // Repository som hanterar alla databasanrop
    CustomerRepository customerRepository;

    /**
     * Konstruktor för Customer.CustomerService
     * Initierar repository-lagret
     */
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
    }

    /**
     * Hämtar och visar alla kunder från databasen
     * Service-lagret kan här:
     * - Formatera utskriften
     * - Lägga till affärslogik (t.ex. filtrera bort inaktiva kunder)
     * - Hantera specialfall (t.ex. om listan är tom)
     *
     * @throws SQLException vid problem med databasanrop
     */
    public void showAllUsers() throws SQLException {
        // Hämta alla kunder från repository-lagret
        ArrayList<Customer> customers = customerRepository.getAllCustomers();

        // Kontrollera om vi har några kunder att visa
        if (customers.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        // Skriv ut alla kunder med tydlig formatering
        System.out.println("\n=== Kundlista ===");
        for (Customer customer : customers) {
            System.out.println("ID: " + customer.getCustomerId());
            System.out.println("Namn: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Telefon: " + customer.getPhone());
            System.out.println("Adress: " + customer.getAddress());
            System.out.println("Lösenord: " + customer.getPassword());
            System.out.println("-----------------");
        }
    }

    public Customer getCustomerById(int customerId) throws SQLException {

        Customer customer = customerRepository.getCustomerById(customerId);
        if(customer == null){
            System.out.println("Ingen kund hittades med ID: " + customerId);
            return null;
        }
        return customer;
    }
    public void addCustomer(String namn, String email, String telefon, String adress, String losenord) throws SQLException {

        if (namn == null || namn.trim().isEmpty()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Ogiltig emailadress");
        }

        try {
            customerRepository.addCustomer(namn, email, telefon, adress, losenord);
        } catch (SQLException e) {
            throw new RuntimeException("Kunde inte skapa kund", e);
        }
    }

    public void updateCustomerName(int customerId, String newName) throws SQLException {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Kunde ID inte vara tomt");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("namn kan inte vara tomt");
        }

        try {
            customerRepository.updateCustomerName(customerId, newName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void updateCustomerEmail(int customerId, String newEmail) throws SQLException {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("email kan inte vara tomt");
        }
        if (customerId <= 0) {
            throw new IllegalArgumentException("ID kan inte vara mindre än 1");
        }

        try {
            customerRepository.updateCustomerEmail(customerId, newEmail);
        }
        catch (Exception e) {
            throw new RuntimeException("Kunde inte uppdatera kundens email",e);
        }

    }

    /**
     * Här kan man lägga till fler metoder som t.ex:
     * - getCustomerById
     * - addNewCustomer
     * - updateCustomer
     * - deleteCustomer
     * - findCustomerByEmail
     */
}