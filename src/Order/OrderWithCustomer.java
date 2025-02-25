package Order;

import java.time.LocalDate;

public class OrderWithCustomer {

    private int orderId;
    private LocalDate orderDate;
    private String customerName;
    private String customerEmail;

    public OrderWithCustomer(int orderId, LocalDate orderDate, String customerName, String customerEmail) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "OrderWithCustomer{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
