package Order;

import Product.Product;

import java.sql.SQLException;

public class OrderProduct {

    private OrderRepository orderRepository;

    public OrderProduct() {
        this.orderRepository = new OrderRepository();
    }

    public void newOrderproduct(Product product, int orderId) throws SQLException {

        orderRepository.addOrderProduct(product, orderId);
        System.out.println("Din order är nu lagd.");

    }
}
