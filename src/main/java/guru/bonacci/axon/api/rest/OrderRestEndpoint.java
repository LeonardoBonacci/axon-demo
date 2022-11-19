package guru.bonacci.axon.api.rest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.axon.coreapi.commands.ConfirmOrderCommand;
import guru.bonacci.axon.coreapi.commands.CreateOrderCommand;
import guru.bonacci.axon.coreapi.commands.ShipOrderCommand;
import guru.bonacci.axon.coreapi.queries.FindAllOrderedProductsQuery2;
import guru.bonacci.axon.coreapi.queries.Order;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;


    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        String orderId = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(orderId, "foo")) //TODO what is return type, event?
                             .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderId)))
                             .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderId)));
    }
    
    @GetMapping("/all-orders")
    public CompletableFuture<List<Order>> findAllOrders() {
        return queryGateway.query(new FindAllOrderedProductsQuery2(), ResponseTypes.multipleInstancesOf(Order.class));
    }
}