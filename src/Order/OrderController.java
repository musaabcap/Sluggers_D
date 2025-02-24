package Order;
import Product.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;
public class OrderController {

    OrderService orderService;
    Scanner scanner;
    ProductController productController;

    public OrderController() throws SQLException {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        while(true){
            try{
                System.out.println("\n=== Orderhantering ===");
                System.out.println("1. Get all orders with product information");
                System.out.println("2. Make a new order");
                System.out.println("3. Show order with customer information");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                int select = scanner.nextInt();

                switch(select){
                    case 1:
                        ArrayList<Order> orders = orderService.getAllOrdersWithProducts();
                        for (Order order : orders) {
                            System.out.println("\nOrder ID: " + order.getOrderId());
                            System.out.println("Orderdatum: " + order.getOrderDate());
                            System.out.println("Kund ID: " + order.getCustomerId());

                            // Skriv ut alla produkter i ordern
                            System.out.println("Produkter i ordern:");
                            for (Product product : order.getProducts()) {
                                System.out.println("- " + product.getName() +
                                        ", Antal: " + product.getStockQuantity() +
                                        ", Pris: " + product.getPrice() + " kr");
                            }
                            System.out.println("------------------------");

                        }
                        break;
                    case 2:
                        productController.productMenu();
                        break;
                    case 3:
                        orderService.getOrderWithCustomerInfo();
                        break;
                    case 0:
                        System.out.println("0. Avslutar orderhantering");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch(SQLException e){
                System.out.println("Ett fel uppstod vid databasanrop: "+e.getMessage());
            }
            catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }

        }
    }

    public void newOrderMenu() throws SQLException {

        System.out.println("Ange dit ID: ");
        int customerId = scanner.nextInt();

        orderService.makeOrder(customerId);


    }


}
