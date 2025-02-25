import Customer.CustomerController;
import Order.OrderController;
import Product.ProductController;
import Product.ProductRepository;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        CustomerController customerController = new CustomerController();

        OrderController orderController = new OrderController();

        ProductController productController = new ProductController();

        //customerController.run();

        //orderController.run();

        productController.run();



    }
}