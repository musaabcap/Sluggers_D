package Order;
import java.util.Scanner;
import java.sql.SQLException;

public class OrderController {

    OrderService orderService;
    Scanner scanner;

    public OrderController() {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        while(true){
            try{
                System.out.println("\n=== Orderhantering ===");
                System.out.println("1. Get all orders");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                int select = scanner.nextInt();

                switch(select){
                    case 1:
                        orderService.getAllOrders();
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
