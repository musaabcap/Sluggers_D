package Product;
import Customer.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Controller-klass för produkthantering
 * Hanterar användarinteraktion för produktrelaterade operationer
 */
public class ProductController {

    // Service-lager för produkthantering, hanterar affärslogik
    ProductService productService;

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
    public void run() {
        while (true) {
            try {
                // Skriv ut menyalternativ direkt i run-metoden för tydlighet
                System.out.println("\n=== Kundhantering ===");
                System.out.println("1. Visa alla produkter");
                System.out.println("0. Avsluta");
                System.out.print("Välj ett alternativ: ");

                // Läs användarens val
                int select = scanner.nextInt();

                // Hantera användarens val
                switch (select) {
                    case 1:
                        // Anropa service-lagret för att visa alla kunder
                        productService.showAllProducts();
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
}
