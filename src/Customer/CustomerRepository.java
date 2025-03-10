package Customer;

import java.sql.*;
import java.util.ArrayList;

/**
 * Repository-klass för kundhantering
 * Hanterar alla databasoperationer för Customer.Customer-entiteten
 * Innehåller även databasanslutning för att göra koden tydligare
 */
public class CustomerRepository {

    /**
     * URL till SQLite-databasen
     * Denna används i varje metod för att ansluta till databasen
     */
    private static final String URL = "jdbc:sqlite:webbutiken.db";

    /**
     * Hämtar alla kunder från databasen
     * Skapar en ny anslutning, hämtar data och stänger anslutning automatiskt
     *
     * @return ArrayList med alla kunder
     * @throws SQLException vid problem med databasanrop
     */
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        // try-with-resources stänger automatiskt Connection, Statement och ResultSet
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            // Loopa igenom alla rader från databasen
            while (rs.next()) {
                // Skapa ett nytt Customer.Customer-objekt från varje databasrad
                Customer customer = new Customer(
                        rs.getInt("customer_id"),     // Hämta ID från customer_id kolumnen
                        rs.getString("name"),   // Hämta förnamn
                        rs.getString("email"),    // Hämta email
                        rs.getString("phone"),         // Hämta email
                        rs.getString("address"),
                        rs.getString("password")
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer getCustomerById(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?")) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("password")
                );
            }
            throw new SQLException("Customer not found");
        }
    }

    public void addCustomer(String name, String email, String phone, String adress, String password) throws SQLException {

        String sql = "INSERT INTO customers (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, adress);
            pstmt.setString(5, password);
            int affected = pstmt.executeUpdate();  // Sparar antalet påverkade rader
            if (affected != 1) {  // Om antalet INTE är 1
                throw new SQLException("Kunde inte lägga till kund i databasen");
            }
        }

    }

    public Customer updateCustomerName(int customerId, String name) throws SQLException {
        String sql = "UPDATE customers SET name = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setInt(2, customerId);
            int affected = pstmt.executeUpdate();
            if (affected != 1) {
                throw new SQLException("Lyckades inte uppdatera kunden");
            }
        }
        // Använder getCustomerById som finns här i repositoryn
        return getCustomerById(customerId);
    }

    public void updateCustomerEmail(int customerId, String email) throws SQLException {
        String sql = "UPDATE customers SET email = ? WHERE customer_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
                 pstmt.setString(1, email);
                 pstmt.setInt(2, customerId);
                 int affected = pstmt.executeUpdate();
                 if (affected != 1) {
                     throw new SQLException("Lyckades inte uppdatera kunden "+email);
                 }
                 System.out.println("Konto med id: " + customerId + " har uppdaterats");
        }
    }

    public void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected != 1) {
                throw new SQLException("Lyckades inte radera kund med kund id: "+customerId);
            }
            System.out.println("Konto med id: " + customerId + " har raderats");
        }
    }


    /**
     * Här kan fler metoder läggas till som t.ex:
     * - addCustomer
     * - getCustomerById
     * - updateCustomer
     * - deleteCustomer
     * - findCustomerByEmail
     *
     * Varje metod kommer följa samma mönster:
     * 1. Skapa Connection med DriverManager.getConnection(URL)
     * 2. Skapa Statement eller PreparedStatement
     * 3. Utför databasoperationen
     * 4. Hantera resultatet
     * 5. Låt try-with-resources stänga alla resurser
     */

}