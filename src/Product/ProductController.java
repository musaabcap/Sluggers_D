package Product;
import Customer.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;
import Order.*;

/**
 * Controller-klass för produkthantering
 * Hanterar användarinteraktion för produktrelaterade operationer
 */
public class ProductController {

    // Service-lager för produkthantering, hanterar affärslogik
    ProductService productService;
    OrderController orderController;

    // Scanner för användarinput
    Scanner scanner;

    /**
     * Konstruktor för Produkt.ProduktController
     * Initierar service och scanner
     */
    public ProductController() {
        // Skapa instanser av nödvändiga objekt
        this.productService = new ProductService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Huvudloop för produkthantering
     * Visar meny och hanterar användarval
     */
    public void productMenu() {
        while (true) {
            try {
                // Skriv ut menyalternativ direkt i run-metoden för tydlighet
                System.out.println("\n=== Produkt hantering ===");
                System.out.println("1. Visa alla produkter");
                System.out.println("2. Sortera produkter");
                System.out.println("3. Lägg till vara i varukorgen");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                int select = scanner.nextInt();

                // Hantera användarens val
                switch (select) {
                    case 1:
                        // Anropa service-lagret för att visa alla produkter
                        productService.showAllProducts();
                        System.out.println("Lägg till produkter i varukorgen");
                        break;
                    case 2:
                        sortProducts();
                        break;
                    case 3:
                        orderController.newOrderMenu();
                        break;
                    case 0:
                        System.out.println("Avslutar produkthantering...");
                        return;
                    default:
                        System.out.println("Ogiltigt val, försök igen");
                }
            } catch (SQLException e) {
                // Hantera databasfel
                System.out.println("Ett fel uppstod vid databasanrop: " + e.getMessage());
            } catch (Exception e) {
                // Hantera övriga fel (t.ex. felaktig input)
                System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
                scanner.nextLine(); // Rensa scanner-bufferten vid felinmatning
            }
        }
    }

    public void sortProducts() {

        while (true) {
            try {
                System.out.println("\n=== Sortera produkter ===");
                System.out.println("1. Sortera efter namn");
                System.out.println("2. Sortera efter kategori");
                System.out.println("3. Sortera efter pris");
                System.out.println("4. Sortera efter antal");
                System.out.println("5. Avancerad sortering");
                System.out.println("0. Gå tillbaka");

                int select = scanner.nextInt();

                switch (select) {
                    case 1:
                        productService.sortProductsByName();
                        break;
                    case 2:
                        break;
                    case 3:
                        productService.sortProductsByPrice();
                        break;
                    case 4:
                        productService.sortProductsByStockQuantity();
                        break;
                    case 5:
                        productService.sortProductsByCategory();
                        break;
                    case 0:
                        System.out.println("Går tillbaka");
                        return;
                    default:
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void advancedSortProductsMenu() {
        while (true) {
            System.out.println("\n=== Avancerad sortering ===");
        }
    }
}