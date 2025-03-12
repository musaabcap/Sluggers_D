package Order;
import Customer.CustomerService;
import Product.ProductService;
import Product.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private CustomerService customerService;
    private ProductRepository productRepository;
    private Product product;
    private OrderProduct orderProduct;
    private ShoppingCart shoppingCart;
    //Döper min logger till klassnamnet.
    private static Logger logger = Logger.getLogger(OrderService.class.getName());


    Scanner scanner = new Scanner(System.in);

    public OrderService() throws SQLException {
        this.orderRepository = new OrderRepository();
        this.productService = new ProductService();
        this.customerService = new CustomerService();
        this.productRepository = new ProductRepository();
        this.orderProduct = new OrderProduct();
        this.shoppingCart = new ShoppingCart();
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        OrderService.logger = logger;
    }

    /*public void getAllOrders() throws SQLException {

        ArrayList <Order> orders = orderRepository.getOrders();

        if(orders.isEmpty()){
            System.out.println("No orders found");
            return;
        }

        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }*/

    /**
     * Skapar en ny beställning i systemet
     */
    public int makeOrder() throws SQLException {
        System.out.println("Ange dit ID: ");
        int customerId = scanner.nextInt();

        LocalDateTime dateNow = LocalDateTime.now();

        // Skapar en ny beställning i databasen med datum/tid och kund-ID
        // och får tillbaka det nya beställningsnumret
        int newOrderId = orderRepository.addOrder(dateNow, customerId);
        return newOrderId;
    }


    /**
     * Lägger till en produkt i kundvagnen
     */
    public Product addProductToShoppingCart() throws SQLException {
                // Anropar metoden addProduct() från ShoppingCart-klassen för att välja en produkt
        // och lägga till den i kundvagnen
        Product product = shoppingCart.addProduct();
        //Jag har skapat en ny klass som endast ska ta hand om shoppingCart. Klassen heter ShoppingCart och metoden addProduct
        // Returnerar den valda produkten så att den kan användas senare
        // (t.ex. för att koppla ihop med en beställning)
        return product;
    }


    public ArrayList<Order> getAllOrdersWithProducts() throws SQLException {
        // Hämta alla grundläggande orderinformation, dessa innehåller inga produkter ännu
        ArrayList<Order> orders = orderRepository.getExistingOrders();

        // Denna metod går till databasen och hämtar alla produkter som tillhör just den specifika ordern (baserat på order_id)
        // Vi behöver ändra Order-objektet genom att lägga till produkter i dess products-lista.
        for (Order order : orders) {
            orderRepository.addProductsToOrder(order);
        }

        return orders;
    }

    public void getOrderWithCustomerInfo() throws SQLException {
        ArrayList <OrderWithCustomer> orderCustomerList = orderRepository.getOrdersWithCustomerInfo();
        if(orderCustomerList.isEmpty()){
            System.out.println("No orders found");
            return;
        }
        for (OrderWithCustomer orderWithCustomer : orderCustomerList) {
            System.out.println(orderWithCustomer.toString());
        }

    }

    public ArrayList<Order> getOrdersWithCustomerInfo(int customerId) throws SQLException {
        // Validera customerId
        logger.log(Level.INFO, "Försöker hitta ordrar för kund-id: " + customerId);

        if (customerId < 1) {
            logger.warning("Ogiltigt kund-ID angivet: " + customerId);
            throw new IllegalArgumentException("Kund-ID måste vara ett positivt heltal.");
        }

        try {
            // Logga innan databasanrop
            logger.info("Utför databasanrop för att hämta ordrar för kund: " + customerId);

            // Hämta ordrar
            ArrayList<Order> orders = orderRepository.getOrdersWithProductsByCustomer(customerId);

            // Logga resultatet från databasanropet
            if (orders.isEmpty()) {
                logger.info("Inga ordrar hittades för kund med id: " + customerId);
            } else {
                logger.info("Hittade " + orders.size() + " ordrar för kund med id: " + customerId);
            }
            return orders;
        } catch (SQLException e) {
            // Logga SQL-fel
            logger.severe("Databasfel vid hämtning av ordrar för kund " + customerId + ": " + e.getMessage());
            // Omvandla till RuntimeException med tydligt meddelande och behåll originalfelet
            throw new RuntimeException("Kunde inte hämta kundens orderhistorik", e);
        }
    }
}
