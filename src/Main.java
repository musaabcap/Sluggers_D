import Customer.CustomerController;
import Product.ProductController;
import Product.ProductRepository;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        CustomerController customerController = new CustomerController();


        ProductController productController = new ProductController();

        customerController.run();

    }
}