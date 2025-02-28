package Order;
import Product.Product;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderRepository {

    private static final String URL = "jdbc:sqlite:webbutiken.db";



    public int addOrder(LocalDateTime dateNow, int customerId) throws SQLException {
        int generatedOrderId = -1; // Defaultvärde om något går fel
        try (Connection conn = DriverManager.getConnection(URL)) {
            // Använd PreparedStatement istället för Statement för säkerhet
            String query = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Sätt parametrarna
            // Konvertera LocalDateTime till java.sql.Date
            Timestamp sqlTimestamp = Timestamp.valueOf(dateNow);
            pstmt.setInt(1, customerId);
            pstmt.setTimestamp(2, sqlTimestamp);

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedOrderId = generatedKeys.getInt(1); // Första kolumnen innehåller order_id
                }
            }
            return generatedOrderId; // Returnerar det nya order_id
        }

    }

    public void addOrderProduct(Product product, int orderId) throws SQLException {
        // Se till att product inte är null innan du försöker lägga till den
        if (product == null) {
            throw new IllegalArgumentException("Product is null, cannot add to order.");
        }

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)"
             )) {

            // Antag att orderId är ett fält i OrderRepository, annars skicka med det som parameter
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, product.getProductId());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getPrice());

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
    public void addProductsToOrder(Order order) throws SQLException {
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
                    Product product = new Product(productId, name, price, quantity);

                    order.addProduct(product);
                }
            }
        }
    }




}
