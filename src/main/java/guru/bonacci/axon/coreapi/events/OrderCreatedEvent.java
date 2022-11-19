package guru.bonacci.axon.coreapi.events;

import lombok.Value;

@Value
public class OrderCreatedEvent {

    private final String orderId;
    private final String productId;
}
