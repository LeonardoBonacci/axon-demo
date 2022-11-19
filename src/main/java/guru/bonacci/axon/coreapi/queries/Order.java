package guru.bonacci.axon.coreapi.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Order {

    private final String orderId;
    private final String productId;
    private OrderStatus orderStatus;

    public Order(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
        orderStatus = OrderStatus.CREATED;
    }

    public void setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }
}