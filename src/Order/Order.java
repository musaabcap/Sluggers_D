package Order;
import Product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private LocalDateTime orderDate;
    private List<Product> products;
    private double totalPrice;

    // Konstruktur för existerande ordrar
    public Order(int orderId, LocalDateTime orderDate, int customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.products = new ArrayList<>();
    }

    // I Order-klassen
    public void addProduct(Product product) {
        this.products.add(product);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", products=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public String displayOrderWithProducts() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nOrder ID: ").append(orderId)
                .append("\nOrderdatum: ").append(orderDate)
                .append("\nKund ID: ").append(customerId)
                .append("\nProdukter i ordern:");

        for (Product product : products) {
            sb.append("\n- ").append(product.getName())
                    .append(", Antal: ").append(product.getQuantity())
                    .append(", Pris: ").append(product.getPrice()).append(" kr")
                    .append(", Totalt: ").append(product.getPrice() * product.getQuantity()).append(" kr");
        }

        sb.append("\n------------------------");

        return sb.toString();
    }


}
