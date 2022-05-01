package com.example.cqrs.domain.event;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Ethan Zhang
 * @date 2022/5/1
 */
@Data
public class ProductSelectedEvent {

    @TargetAggregateIdentifier
    private UUID foodCartId;

    private UUID productId;

    private Integer quantity;

    public ProductSelectedEvent() {
    }

    public ProductSelectedEvent(UUID foodCartId, UUID productId, Integer quantity) {
        this.foodCartId = foodCartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
