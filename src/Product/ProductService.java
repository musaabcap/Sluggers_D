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
        // Hämta alla produkter från repository-lagret
        ArrayList<Product> serviceProducts = productRepository.getAllProducts();

        // Kontrollera om vi har några produkter att visa
        if (serviceProducts.isEmpty()) {
            System.out.println("Inga kunder hittades.");
            return;
        }

        // Skriv ut alla produkter med tydlig formatering
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

    public void sortProductsByName() throws SQLException {
        List<String> sortedProducts = productRepository.getAllProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .map(p -> p.getName() + " - " + p.getPrice() + " SEK")
                .toList();
        sortedProducts.forEach(System.out::println);
    }

    public void sortProductsByPrice() throws SQLException {
        List<String> sortedProducts = productRepository.getAllProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .map(p -> p.getName() + " - " + p.getPrice() + " SEK")
                .toList();
        sortedProducts.forEach(System.out::println);
    }

    public void sortProductsByStockQuantity() throws SQLException {
        List<String> sortedProducts = productRepository.getAllProducts()
                .stream()
                .sorted(Comparator.comparing(Product::getStockQuantity))
                .map(p -> p.getName() + " - " + p.getStockQuantity() + " Antal")
                .toList();
        sortedProducts.forEach(System.out::println);
    }

    public void sortProductsByCategory() throws SQLException {
        List<String> sortedProducts = productRepository.getProductsSortedByCategory();
        sortedProducts.forEach(System.out::println);
    }

    public Product getProductByName(String productName) throws SQLException {
        if(productName == null) {
            System.out.println("Ingen produkt hittades.");
            return null;
        }
        return productRepository.findByName(productName);
    }

    public Product getProductById(int productId) throws SQLException {
        if(productId < 0) {
            System.out.println("Produkt id måste vara ett positivt nummer");
            return null;
        }
        return productRepository.getProductById(productId);
    }

    public Product updateProductStock(int productId, int quantityToReduce) throws SQLException {
        return productRepository.updateProductStockQuantity(productId, quantityToReduce);
    }

    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        // Validering av produktID och pris
        if (productId <= 0) {
            throw new IllegalArgumentException("Produkt-ID måste vara större än 0");
        }

        if (newPrice < 0) {
            throw new IllegalArgumentException("Priset kan inte vara negativt");
        }

        // Utför uppdateringen om valideringen passerar
        productRepository.updateProductPrice(productId, newPrice);
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
