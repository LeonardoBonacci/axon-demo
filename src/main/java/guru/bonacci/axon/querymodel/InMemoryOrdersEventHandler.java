package guru.bonacci.axon.querymodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

import guru.bonacci.axon.coreapi.events.OrderConfirmedEvent;
import guru.bonacci.axon.coreapi.events.OrderCreatedEvent;
import guru.bonacci.axon.coreapi.events.OrderShippedEvent;
import guru.bonacci.axon.coreapi.queries.FindAllOrderedProductsQuery2;
import guru.bonacci.axon.coreapi.queries.Order;
import guru.bonacci.axon.coreapi.queries.OrderUpdatesQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InMemoryOrdersEventHandler implements OrdersEventHandler {
	
    private final Map<String, Order> orders = new HashMap<>();
    private final QueryUpdateEmitter emitter;
    
    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        orders.put(orderId, new Order(orderId, event.getProductId()));
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orders.computeIfPresent(event.getOrderId(), (orderId, order) -> {
            order.setOrderConfirmed();
            emitUpdate(order);
            return order;
        });
    }
    
    @EventHandler
    public void on(OrderShippedEvent event) {
        orders.computeIfPresent(event.getOrderId(), (orderId, order) -> {
            order.setOrderShipped();
            emitUpdate(order);
            return order;
        });
    }
    
    @QueryHandler
    public List<Order> handle(FindAllOrderedProductsQuery2 query) {
        return new ArrayList<>(orders.values());
    }
    
    private void emitUpdate(Order order) {
        emitter.emit(OrderUpdatesQuery.class, q -> order.getOrderId()
          .equals(q.getOrderId()), order);
    }
}
