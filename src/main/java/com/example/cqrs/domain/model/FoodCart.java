package com.example.cqrs.domain.model;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import com.example.cqrs.domain.ProductDeselectionException;
import com.example.cqrs.domain.command.ConfirmOrderCommand;
import com.example.cqrs.domain.command.CreateFoodCartCommand;
import com.example.cqrs.domain.command.DeselectProductCommand;
import com.example.cqrs.domain.command.SelectProductCommand;
import com.example.cqrs.domain.event.FoodCartCreatedEvent;
import com.example.cqrs.domain.event.OrderConfirmedEvent;
import com.example.cqrs.domain.event.ProductDeselectedEvent;
import com.example.cqrs.domain.event.ProductSelectedEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Aggregate
class FoodCart {

    private static final Logger logger = LoggerFactory.getLogger(FoodCart.class);

    @AggregateIdentifier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;
    private boolean confirmed;

    public FoodCart() {
        // Required by Axon
    }

    @CommandHandler
    public FoodCart(CreateFoodCartCommand command) {
        log.info(Thread.currentThread().getName());
        apply(new FoodCartCreatedEvent(command.getFoodCartId()));
    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        apply(new ProductSelectedEvent(foodCartId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand command) throws ProductDeselectionException {
        UUID productId = command.getProductId();
        int quantity = command.getQuantity();

        if (!selectedProducts.containsKey(productId)) {
            throw new ProductDeselectionException(
                    "Cannot deselect a product which has not been selected for this Food Cart"
            );
        }
        if (selectedProducts.get(productId) - quantity < 0) {
            throw new ProductDeselectionException(
                    "Cannot deselect more products of ID [" + productId + "] than have been selected initially"
            );
        }

        apply(new ProductDeselectedEvent(foodCartId, productId, quantity));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (confirmed) {
            logger.warn("Cannot confirm a Food Cart order which is already confirmed");
            return;
        }

        apply(new OrderConfirmedEvent(foodCartId));
    }

    @SneakyThrows
    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("EventSourcingHandler received FoodCartCreatedEvent ");

        log.info(Thread.currentThread().getName());

        foodCartId = event.getFoodCartId();
        selectedProducts = new HashMap<>();
        confirmed = false;

        log.info("EventSourcingHandler processed FoodCartCreatedEvent");
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(event.getProductId(), event.getQuantity(), Integer::sum);
    }

    @EventSourcingHandler
    public void on(ProductDeselectedEvent event) {
        selectedProducts.computeIfPresent(
                event.getProductId(),
                (productId, quantity) -> quantity -= event.getQuantity()
        );
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        confirmed = true;
    }
}
