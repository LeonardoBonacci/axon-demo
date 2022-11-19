package guru.bonacci.axon.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CreateOrderCommand {

	@TargetAggregateIdentifier
    private final String orderId;
	private final String productId;
}
