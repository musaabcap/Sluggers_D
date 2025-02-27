package Order;
import Product.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.SQLException;
public class OrderController {

    OrderService orderService;
    Scanner scanner;
    ProductController productController;
    OrderProduct orderProduct;

    public OrderController() throws SQLException {
        this.orderService = new OrderService();
        this.orderProduct = new OrderProduct();
        orderProduct = new OrderProduct();
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
                            System.out.println(order.displayOrderWithProducts());
                        }
                        break;
                    case 2:

                        int newOrderId = orderService.makeOrder(); //Här kallar jag på makeOrder för att skapa ett nytt order id och sparar i en variable
                        Product product = orderService.addProductToShoppingCart(); //Här sparar jag product objekt i variablen
                        orderProduct.newOrderproduct(product, newOrderId); //Här skickar jag vidare variablerna
                        //Product shoppingCartProduct = orderService.addProductToShoppingCart();
                        //orderProduct.newOrderproduct(shoppingCartProduct, newOrderId);
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



}
