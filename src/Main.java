import Customer.*;
import Product.*;
import Order.*;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        CustomerController customerController = new CustomerController();

        OrderController orderController = new OrderController();

        ProductController productController = new ProductController();

        //customerController.run();

        orderController.run();

        //productController.productMenu();



    }
}