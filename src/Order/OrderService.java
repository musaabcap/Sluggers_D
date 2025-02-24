package Order;
import Customer.CustomerService;
import Product.ProductService;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;
    private CustomerService customerService;

    public OrderService() throws SQLException {
        this.orderRepository = new OrderRepository();
        this.productService = new ProductService();
        this.customerService = new CustomerService();
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

    public void makeOrder(int customerId) throws SQLException {
        LocalDateTime dateNow = LocalDateTime.now();
        orderRepository.addOrder(dateNow, customerId);
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
