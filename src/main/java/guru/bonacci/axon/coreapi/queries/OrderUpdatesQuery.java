package guru.bonacci.axon.coreapi.queries;

import lombok.Value;

@Value
public class OrderUpdatesQuery {

	private final String orderId;
}
