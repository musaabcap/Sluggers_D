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

public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private CustomerService customerService;
    private ProductRepository productRepository;
    private Product product;
    private OrderProduct orderProduct;
    private ShoppingCart shoppingCart;


    Scanner scanner = new Scanner(System.in);

    public OrderService() throws SQLException {
        this.orderRepository = new OrderRepository();
        this.productService = new ProductService();
        this.customerService = new CustomerService();
        this.productRepository = new ProductRepository();
        this.orderProduct = new OrderProduct();
        this.shoppingCart = new ShoppingCart();
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

    public int makeOrder() throws SQLException {
        System.out.println("Ange dit ID: ");
        int customerId = scanner.nextInt();

        LocalDateTime dateNow = LocalDateTime.now();
        int newOrderId = orderRepository.addOrder(dateNow, customerId);
        return newOrderId;
    }

    public Product addProductToShoppingCart() throws SQLException {
        Product product = shoppingCart.addProduct(); //Jag har skapat en ny klass som endast ska ta hand om shoppingCart. Klassen heter ShoppingCart och metoden addProduct
        return product; //Skickar tillbaka product objektet
    }



    public ArrayList<Order> getAllOrdersWithProducts() throws SQLException {
        // Först hämta alla ordrar
        ArrayList<Order> orders = orderRepository.getExistingOrders();

        // Sedan lägg till produkter för varje order
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
}
