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
    ProductService productService;

    public OrderController() throws SQLException {
        this.orderService = new OrderService();
        this.orderProduct = new OrderProduct();
        this.orderProduct = new OrderProduct();
        this.productService = new ProductService();
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
                        // Skapar en ny beställning i systemet och får tillbaka ett unikt beställningsnummer
                        int newOrderId = orderService.makeOrder();

                        boolean continueShopping = true;

                        while(continueShopping){
                            productService.showAllProducts();
                            // Lägger till en produkt i kundvagnen och sparar produktinformationen i produkt-objekt
                            Product product = orderService.addProductToShoppingCart();
                            if(product != null){
                                // Det sista steget: Kopplar ihop produkten med beställningen i databasen
                                // så att systemet vet vilka produkter som ingår i vilken beställning
                                orderProduct.newOrderproduct(product, newOrderId);
                            }
                            System.out.println("Vill du lägga till fler produkter? (j/n)");
                            String answer = scanner.next();
                            if (!answer.equalsIgnoreCase("j")) {
                                continueShopping = false;
                            }
                        }
                        // Lägger till bekräftelsemeddelandet här, efter att while-loopen är klar
                        System.out.println("Din order är nu lagd.");
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
