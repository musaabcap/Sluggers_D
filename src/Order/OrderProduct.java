package Order;

import Product.Product;

import java.sql.SQLException;

public class OrderProduct {

    private OrderRepository orderRepository;

    public OrderProduct() {
        this.orderRepository = new OrderRepository();
    }

    public void newOrderproduct(Product product, int orderId) throws SQLException {

        int productId = product.getProductId();

        double price = product.getPrice();

        orderRepository.addOrderProduct(orderId, productId, 1, price);

    }
}
