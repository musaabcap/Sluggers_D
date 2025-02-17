package Order;
import java.sql.*;
import java.util.ArrayList;

public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Order> getOrders() throws SQLException {
        ArrayList<Order> orders = new ArrayList<Order>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM orders")){
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
}
