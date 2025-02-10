package Product;

import Customer.Customer;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    private static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();

        // try-with-resources stänger automatiskt Connection, Statement och ResultSet
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products");) {

            // Loopa igenom alla rader från databasen
            while (rs.next()) {
                // Skapa ett nytt Customer.Customer-objekt från varje databasrad
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
                products.add(product);
            }
        }
        return products;
    }
}
