package Order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private LocalDateTime orderDate;
    private List<String> products;
    private double totalPrice;


    // Konstrukör - för ny order
    public Order(int customerId, List<String> products, double totalPrice) {
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.products = new ArrayList<>(products);
        this.totalPrice = totalPrice;
        this.orderDate = LocalDateTime.now();
    }

    // För existerande
    public Order(int orderId, LocalDateTime orderDate, int customerId) {
        this.orderId = orderId;
        this.orderDate = LocalDateTime.now();
        this.customerId = customerId;
        this.products = new ArrayList<>();
    }

    // I Order-klassen
    /*public void addProduct(Product product) {
        this.products.add(product);
    }*/

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

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Order Date: " + orderDate);
        System.out.println("Products: " + products);
        System.out.println("Total Price: " + totalPrice);
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
}
