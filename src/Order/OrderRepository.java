package Order;
import Product.Product;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

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

                    //Produkterna läggs till direkt i Order-objektet via anropet
                    order.addProduct(product);
                }
            }
        }
    }

    /**
     * Hämtar alla ordrar med tillhörande produkter för en specifik kund.
     * @param customerId ID för kunden vars ordrar ska hämtas
     * @return En lista med Order-objekt som innehåller information om ordrarna och deras produkter
     * @throws SQLException om ett databasfel inträffar
     * @throws IllegalArgumentException om customerId är mindre än 1
     */

    public ArrayList<Order> getOrdersWithProductsByCustomer(int customerId) throws SQLException {
        // Validering av indata - kontrollera att customerId är giltig
        if (customerId < 1) {
            throw new IllegalArgumentException("Kund-ID måste vara ett positivt heltal.");
        }

        // HashMap för att lagra Order-objekt med order_id som nyckel
        // Detta gör att vi kan hitta en specifik order snabbt genom dess ID
        HashMap<Integer, Order> orderMap = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(
                     // SQL-fråga med JOIN för att hämta all data på en gång
                     "SELECT o.order_id, o.order_date, o.customer_id, " +
                             "p.product_id, p.name, p.price, op.quantity " +
                             "FROM orders o " +
                             // LEFT JOIN säkerställer att ordrar utan produkter också hämtas
                             "LEFT JOIN orders_products op ON o.order_id = op.order_id " +
                             "LEFT JOIN products p ON op.product_id = p.product_id " +
                             // WHERE-villkor för att endast hämta ordrar för den specifika kunden
                             "WHERE o.customer_id = ? " +
                             // Sortera efter order_id för att underlätta bearbetning
                             "ORDER BY o.order_id")) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");

                    // Om vi inte redan har skapat ett Order-objekt för denna order_id
                    if (!orderMap.containsKey(orderId)){
                        // Hämta orderinformation
                        LocalDateTime orderDateTime = rs.getTimestamp("order_date").toLocalDateTime();
                        int orderCustomerId = rs.getInt("customer_id");

                        // Skapa ett nytt Order-objekt
                        Order order = new Order(orderId, orderDateTime, orderCustomerId);

                        // Lägg till Order-objektet i HashMap med order_id som nyckel
                        orderMap.put(orderId, order);
                    }
                    //Hämta det befintliga Order-objektet från HashMap
                    //Genom att hämta Order-objektet från HashMap ser du till att du alltid
                    //jobbar med samma Order-objekt för en specifik order, oavsett hur många produkter den har.
                    Order order = orderMap.get(orderId);

                    // Kontrollera om det finns någon produkt för denna order
                    // (om produkt-kolumnerna är NULL från LEFT JOIN)

                    int productId = rs.getInt("product_id");

                    if(!rs.wasNull()){
                        // Om produkten inte är NULL
                        // Hämta produktinformation
                        String name = rs.getString("name");
                        double price = rs.getDouble("price");
                        int quantity = rs.getInt("quantity");

                        // Skapa ett Product-objekt och lägg till i ordern
                        Product product = new Product(productId, name, price, quantity);
                        // Här använder jag referensen från orderMap.get(orderId)
                        order.addProduct(product);
                    }

                }
            }
        }
        if (orderMap.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(orderMap.values());
    }
}

