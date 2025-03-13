package Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        try {// Hämta alla produkter från repository-lagret
            ArrayList<Product> serviceProducts = productRepository.getAllProducts();

            // Kontrollera om vi har några produkter att visa
            if (serviceProducts.isEmpty()) {
                System.out.println("Inga produkter hittades.");
                return;
            }

            // Skriv ut alla produkter med tydlig formatering
            System.out.println("\n=== Produktlista ===");
            for (Product product : serviceProducts) {
                System.out.println("ID: " + product.getProductId());
                System.out.println("Namn: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Stock quantity: " + product.getStockQuantity());
                System.out.println("-----------------");
            }
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkter: " + e.getMessage());
        }
    }

    public void sortProductsByName() {
        try {
            productRepository.getAllProducts().stream()
                    .sorted(Comparator.comparing(Product::getName))
                    .map(p -> p.getName() + " - " + p.getPrice() + " SEK")
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println("Fel vid sortering av produkter: " + e.getMessage());
        }
    }

    public void sortProductsByPrice() throws SQLException {
        try {
            List<String> sortedProducts = productRepository.getAllProducts()
                    .stream()
                    .sorted(Comparator.comparing(Product::getPrice))
                    .map(p -> p.getName() + " - " + p.getPrice() + " SEK")
                    .toList();
            sortedProducts.forEach(System.out::println);
        }
        catch (SQLException e) {
            System.out.println("Fel vid sortering av produkter: " + e.getMessage());
        }
    }

    public void sortProductsByStockQuantity() throws SQLException {
        try {
            List<String> sortedProducts = productRepository.getAllProducts()
                    .stream()
                    .sorted(Comparator.comparing(Product::getStockQuantity))
                    .map(p -> p.getName() + " - " + p.getStockQuantity() + " Antal")
                    .toList();
            sortedProducts.forEach(System.out::println);
        }
        catch (SQLException e) {
            System.out.println("Fel vid sortering av produkter: " + e.getMessage());
        }
    }

    public void sortProductsByCategory() throws SQLException {
        try {
            List<String> sortedProducts = productRepository.getProductsSortedByCategory();
            sortedProducts.forEach(System.out::println);
        }
        catch (SQLException e) {
            System.out.println("Fel vid sortering av produkter: " + e.getMessage());
        }
    }

    public Product getProductByName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            System.out.println("Fel: Produktnamn får inte vara tomt.");
            return null;
        }
        try {
            Product product = productRepository.findByName(productName);
            if (product == null) {
                System.out.println("Ingen produkt hittades med namnet: " + productName);
            }
            return product;
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkt: " + e.getMessage());
            return null;
        }
    }


    public Product getProductById(int productId) {
        if (productId <= 0) {
            System.out.println("Fel: Produkt-ID måste vara ett positivt heltal.");
            return null;
        }
        try {
            Product product = productRepository.getProductById(productId);
            if (product == null) {
                System.out.println("Ingen produkt hittades med ID: " + productId);
            }
            return product;
        } catch (SQLException e) {
            System.out.println("Fel vid hämtning av produkt: " + e.getMessage());
            return null;
        }
    }

    public Product updateProductStock(int productId, int quantityToReduce) throws SQLException {
        return productRepository.updateProductStockQuantity(productId, quantityToReduce);
    }

    public void updateProductPrice(int productId, double newPrice) {
        if (productId <= 0) {
            System.out.println("Fel: Produkt-ID måste vara större än 0.");
            return;
        }
        if (newPrice < 0) {
            System.out.println("Fel: Priset kan inte vara negativt.");
            return;
        }
        try {
            productRepository.updateProductPrice(productId, newPrice);
            System.out.println("Priset har uppdaterats till " + newPrice + " SEK för produkt-ID " + productId);
        } catch (SQLException e) {
            System.out.println("Fel vid uppdatering av pris: " + e.getMessage());
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
