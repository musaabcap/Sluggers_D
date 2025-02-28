package Order;
import Product.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
        System.out.println("Ange produkt Id för att lägga till varan i varukorgen:");
        int input = scanner.nextInt();

        // Hämtar produktinformation från databasen baserat på det angivna ID:t
        Product product = productRepository.getProductById(input);

        System.out.println("Hur många av produkten vill du lägga till?");
        int input2 = scanner.nextInt();

        if(product.getStockQuantity() >= input2) { // Kontrollerar om det finns tillräckligt många produkter i lager
            product.setQuantity(input2); // // Sparar det önskade antalet i produktobjektet
            shoppingCart.add(product); // Lägger till produkten i kundvagnen (shoppingCart-listan)

            // Visar en bekräftelse till användaren med antal och produktnamn
            System.out.println("Antal " + product.getQuantity() + " St, har laggts till." +
                    " Av produkten " + product.getName());
            return product;
        }
        else {
            System.out.println("Det finns bara " + product.getQuantity() + " St, på lagret.");
            return null;
        }
    }
}
