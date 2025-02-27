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

    public Product addProduct() throws SQLException {
        System.out.println("Ange produkt Id för att lägga till varan i varukorgen:");
        int input = scanner.nextInt();
        Product product = productRepository.getProductById(input); //Hämtar produkt id från repository klassen där input besätmmer vilken produkt.

        System.out.println("Hur många av produkten vill du lägga till?");
        int input2 = scanner.nextInt();

        if(product.getStockQuantity() >= input2) { //Här har jag en if sats för att se till så att mängden som användaren vill beställa finns i databasen.
            product.setQuantity(input2); //Sätter en temporär quantity för att spara i shoppingCart
            shoppingCart.add(product); //Här lägger jag till den nya produkten i listan
            System.out.println("Antal " + product.getQuantity() + " St, har laggts till." + //Här skriver jag ut så att det blir rätt
                    " Av produkten " + product.getName());
            return product;
        }
        else {
            System.out.println("Det finns bara " + product.getQuantity() + " St, på lagret.");
            return null;
        }
    }
}
