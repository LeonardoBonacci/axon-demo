package guru.bonacci.axon.coreapi.events;

import lombok.Value;

@Value
public class OrderShippedEvent {

    private final String orderId;
}
