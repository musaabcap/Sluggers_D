package Order;
import java.time.LocalDate;

public class Order {
    private Long orderId;
    private LocalDate orderDate;

    public Order(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
