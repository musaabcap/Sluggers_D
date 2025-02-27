package Product;
import Customer.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                }
            }
        }
        throw new SQLException("Product not found");
    }

    public List<String> getProductsSortedByCategory() throws SQLException {
        List<String> sortedProducts = new ArrayList<>();
        String sql = "        SELECT c.name AS category_name, p.name AS product_name, p.price, p.stock_quantity" +
                "        FROM products p" +
                "        JOIN products_categories pc ON p.product_id = pc.product_id" +
                "        JOIN categories c ON pc.category_id = c.category_id" +
                "        ORDER BY c.name ASC, p.name ASC;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                 while (rs.next()) {
                     String categoryName = rs.getString("category_name");
                     String productName = rs.getString("product_name");
                     double price = rs.getDouble("price");
                     int stockQuantity = rs.getInt("stock_quantity");

                     sortedProducts.add(categoryName + " - " + productName + " - " + price + " - " + stockQuantity);
                 }
        }
        return sortedProducts;
    }

}
