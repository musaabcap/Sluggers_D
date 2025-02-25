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

    public void getAllOrders() throws SQLException {

        ArrayList <Order> orders = orderRepository.getOrders();

        if(orders.isEmpty()){
            System.out.println("No orders found");
            return;
        }

        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }

    public void makeOrder(int customerId) throws SQLException {
        Order order = new Order(LocalDate.now());
        orderRepository.addOrder(order, customerId);
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
