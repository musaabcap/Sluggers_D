package Order;
import Product.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class ShoppingCart {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Product> shoppingCart;
    ProductRepository productRepository;
    Product product;

    public ShoppingCart() {
        shoppingCart = new ArrayList<>();
        productRepository = new ProductRepository();
    }


    /**
     * Låter användaren välja en produkt och ange antal att lägga i kundvagnen
     */
    public Product addProduct() throws SQLException {
        int productId;
        int quantity;

        // Läs in ett giltigt produkt-ID
        while (true) {
            try {
                System.out.print("Ange produkt ID för att lägga till varan i varukorgen: ");
                productId = scanner.nextInt();

                if (productId <= 0) {
                    System.out.println("Fel: Produkt-ID måste vara ett positivt heltal. Försök igen.");
                } else {
                    break; // Giltig input
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Fel: Ange ett giltigt heltal.");
                scanner.next(); // Rensar input
            }
        }

        try {
            // Hämta produkt från databasen
            Product product = productRepository.getProductById(productId);

            // Kontrollera om produkten finns
            if (product == null) {
                System.out.println("Fel: Ingen produkt hittades med ID: " + productId);
                return null;
            }

            // Läs in en giltig kvantitet
            while (true) {
                try {
                    System.out.print("Hur många av produkten vill du lägga till? ");
                    quantity = scanner.nextInt();

                    if (quantity < 0) {
                        System.out.println("Fel: Kvantiteten måste vara minst 1. Försök igen.");
                    } else {
                        break; // Giltig input
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Fel: Ange ett giltigt heltal.");
                    scanner.next(); // Rensar  input
                }
            }

            // Kontrollera om det finns tillräckligt många produkter i lager
            if (product.getStockQuantity() >= quantity) {
                product.setQuantity(quantity);
                shoppingCart.add(product);
                System.out.println("Lagt till " + quantity + " st av " + product.getName() + " i varukorgen.");
                return product;
            } else {
                System.out.println("Fel: Endast " + product.getStockQuantity() + " st finns i lager.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Databasfel vid hämtning av produkt: " + e.getMessage());
            return null;
        }
    }
}
