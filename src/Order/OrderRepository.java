package Order;
import Product.Product;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webbutiken.db";



    public void addOrder(Order order, int customerId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // Använd PreparedStatement istället för Statement för säkerhet
            String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Sätt parametrarna
            // Konvertera LocalDateTime till java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(order.getOrderDate().toLocalDate());
            pstmt.setInt(1, customerId);
            pstmt.setDate(2, sqlDate);

            pstmt.executeUpdate();
        }
    }

    public ArrayList<OrderWithCustomer> getOrdersWithCustomerInfo() throws SQLException {
        ArrayList<OrderWithCustomer> orderCustomerList = new ArrayList<OrderWithCustomer>();

        try (Connection conn = DriverManager.getConnection(URL)) {
            String query = """
            SELECT orders.order_id, orders.order_date, orders.customer_id, customers.name, customers.email 
            FROM orders 
            JOIN customers ON orders.customer_id = customers.customer_id
            """;

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderWithCustomer orderWithCustomer = new OrderWithCustomer(
                        rs.getInt("order_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("name"),
                        rs.getString("email")
                );
                orderCustomerList.add(orderWithCustomer);
            }
        }
        return orderCustomerList;
    }


    // I OrderRepository klassen
    public ArrayList<Order> getExistingOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT order_id, order_date, customer_id FROM orders")) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDateTime orderDateTime = rs.getTimestamp("order_date").toLocalDateTime();
                int customerId = rs.getInt("customer_id");

                // Skapa Order-objekt med befintlig konstruktor
                Order order = new Order(orderId, orderDateTime, customerId);

                orders.add(order);
            }
        }

        return orders;
    }

    // Lägg till produkter för en specifik order
    /*public void addProductsToOrder(Order order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT products.product_id, products.name, products.price, orders_products.quantity " +
                             "FROM orders_products " +
                             "JOIN products ON orders_products.product_id = products.product_id " +
                             "WHERE orders_products.order_id = ?")){

            pstmt.setInt(1, order.getOrderId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");

                    // Skapa Product-objekt och lägg till i ordern

                    Product product = new Product(productId, name, price);


                    order.addProduct(product);
                }
            }
        }
    }*/

}
