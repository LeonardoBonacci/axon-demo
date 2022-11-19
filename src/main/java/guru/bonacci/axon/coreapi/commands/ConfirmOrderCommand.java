package guru.bonacci.axon.coreapi.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class ConfirmOrderCommand {

	@TargetAggregateIdentifier
    private final String orderId;
}
