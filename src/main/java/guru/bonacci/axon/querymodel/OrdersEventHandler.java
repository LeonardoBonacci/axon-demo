package guru.bonacci.axon.querymodel;

import java.util.List;

import guru.bonacci.axon.coreapi.events.OrderConfirmedEvent;
import guru.bonacci.axon.coreapi.events.OrderCreatedEvent;
import guru.bonacci.axon.coreapi.events.OrderShippedEvent;
import guru.bonacci.axon.coreapi.queries.FindAllOrderedProductsQuery2;
import guru.bonacci.axon.coreapi.queries.Order;

public interface OrdersEventHandler {

	void on(OrderCreatedEvent event);	
	
	void on(OrderConfirmedEvent event);

	void on(OrderShippedEvent event);
	
	List<Order> handle(FindAllOrderedProductsQuery2 query);
}
