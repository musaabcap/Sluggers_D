package Order;
import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<Order>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getDate("order_date").toLocalDate()
                );
                orders.add(order);
            }
        }
        return orders;
    }

    public void addOrder(Order order, int customerId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL)) {
            // Använd PreparedStatement istället för Statement för säkerhet
            String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            // Sätt parametrarna
            pstmt.setInt(1, customerId);
            pstmt.setDate(2, java.sql.Date.valueOf(order.getOrderDate()));

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

}
