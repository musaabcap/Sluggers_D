package Product;
import Customer.Customer;
import Customer.CustomerRepository;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Service-klass för produkthantering
 * Innehåller affärslogik mellan controller och repository
 */
public class ProductService {

    // Repository som hanterar alla databasanrop
    ProductRepository productRepository;

    /**
     * Konstruktor för Produkt.Produktervice
     * Initierar repository-lagret
     */
    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    /**
     * Hämtar och visar alla Produkt från databasen
     * Service-lagret kan här:
     * - Formatera utskriften
     * - Lägga till affärslogik (t.ex. filtrera produkter)
     * - Hantera specialfall (t.ex. om listan är tom)
     *
     * @throws SQLException vid problem med databasanrop
     */
    public void showAllProducts() throws SQLException {
        // Hämta alla kunder från repository-lagret
        ArrayList<Product> serviceProducts = productRepository.getAllProducts();

        // Kontrollera om vi har några kunder att visa
        if (serviceProducts.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        // Skriv ut alla kunder med tydlig formatering
        System.out.println("\n=== Kundlista ===");
        for (Product product : serviceProducts) {
            System.out.println("ID: " + product.getProductId());
            System.out.println("Namn: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Stock quantity: " + product.getStockQuantity());
            System.out.println("-----------------");
        }
    }

    /**
     * Här kan man lägga till fler metoder som t.ex:
     * - getProduktById
     * - addNewProdukt
     * - updateprodukt
     * - deleteProdukt
     * - findProduktByEmail
     */
}
