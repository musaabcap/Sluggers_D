package Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderService {

    OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository();
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
}
