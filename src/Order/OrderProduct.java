package Order;
import Product.*;
import java.sql.SQLException;

public class OrderProduct {

    private OrderRepository orderRepository;
    private ProductService productService;

    public OrderProduct() {
        this.orderRepository = new OrderRepository();
        this.productService = new ProductService();
    }

    public void newOrderproduct(Product product, int orderId) throws SQLException {

        orderRepository.addOrderProduct(product, orderId);
        System.out.println("Din order är nu lagd.");

        // Efter att produkten har lagts till i ordern, anropa ProductService för att uppdatera lagersaldot
        Product updatedProduct = productService.updateProductStock(product.getProductId(), product.getQuantity());
        System.out.println("Produktens nya lagersaldo: " + updatedProduct.getStockQuantity());
    }
}
