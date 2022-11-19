package guru.bonacci.axon.commandmodel.order;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import guru.bonacci.axon.coreapi.commands.ConfirmOrderCommand;
import guru.bonacci.axon.coreapi.commands.CreateOrderCommand;
import guru.bonacci.axon.coreapi.commands.ShipOrderCommand;
import guru.bonacci.axon.coreapi.events.OrderConfirmedEvent;
import guru.bonacci.axon.coreapi.events.OrderCreatedEvent;
import guru.bonacci.axon.coreapi.events.OrderShippedEvent;
import guru.bonacci.axon.coreapi.exceptions.UnconfirmedOrderException;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(), command.getProductId()));
    }
    
    @CommandHandler 
    public void handle(ConfirmOrderCommand command) { 
        if (orderConfirmed) {
            return;
        }
        apply(new OrderConfirmedEvent(orderId)); 
    } 

    @CommandHandler 
    public void handle(ShipOrderCommand command) { 
        if (!orderConfirmed) { 
            throw new UnconfirmedOrderException(); 
        } 
        apply(new OrderShippedEvent(orderId)); 
    } 

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        orderConfirmed = false;
    }

    @EventSourcingHandler 
    public void on(OrderConfirmedEvent event) { 
        orderConfirmed = true; 
    }
    
    protected OrderAggregate() { }
}