package guru.bonacci.axon.commandmodel;

import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.bonacci.axon.commandmodel.order.OrderAggregate;
import guru.bonacci.axon.coreapi.commands.ConfirmOrderCommand;
import guru.bonacci.axon.coreapi.commands.CreateOrderCommand;
import guru.bonacci.axon.coreapi.commands.ShipOrderCommand;
import guru.bonacci.axon.coreapi.events.OrderConfirmedEvent;
import guru.bonacci.axon.coreapi.events.OrderCreatedEvent;
import guru.bonacci.axon.coreapi.exceptions.UnconfirmedOrderException;

class OrderAggregateUnitTest {

    private static final String ORDER_ID = UUID.randomUUID().toString();
    private static final String PRODUCT_ID = UUID.randomUUID().toString();

    private FixtureConfiguration<OrderAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    void giveNoPriorActivity_whenCreateOrderCommand_thenShouldPublishOrderCreatedEvent() {
    	String orderId = UUID.randomUUID().toString();
    	String productId = "Deluxe Chair";
    	fixture.givenNoPriorActivity()
    	  .when(new CreateOrderCommand(orderId, productId))
    	  .expectEvents(new OrderCreatedEvent(orderId, productId));    
	}
 
    @Test
    void givenOrderCreatedEvent_whenShipOrderCommand_thenShouldThrowUnconfirmedOrderException() {
    	String orderId = UUID.randomUUID().toString();
    	String productId = "Deluxe Chair";
    	fixture.given(new OrderCreatedEvent(orderId, productId))
    	  .when(new ShipOrderCommand(orderId))
    	  .expectException(UnconfirmedOrderException.class);

    }

//    @Test
//    void givenOrderCreatedEventAndOrderConfirmedEvent_whenShipOrderCommand_thenShouldPublishOrderShippedEvent() {
//        fixture.given(new OrderCreatedEvent(ORDER_ID), new OrderConfirmedEvent(ORDER_ID))
//               .when(new ShipOrderCommand(ORDER_ID))
//               .expectEvents(new OrderShippedEvent(ORDER_ID));
//    }
//    
//    @Test
//    void givenOrderCreatedEvent_whenConfirmOrderCommand_thenShouldPublishOrderConfirmedEvent() {
//    }
//
//    @Test
//    void givenOrderCreatedEventAndOrderConfirmedEvent_whenConfirmOrderCommand_thenExpectNoEvents() {
//        fixture.given(new OrderCreatedEvent(ORDER_ID), new OrderConfirmedEvent(ORDER_ID))
//               .when(new ConfirmOrderCommand(ORDER_ID))
//               .expectNoEvents();
//    }
//    
}