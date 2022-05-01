package com.example.cqrs.domain.event;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Ethan Zhang
 * @date 2022/5/1
 */
@Data
public class OrderConfirmedEvent {

    @TargetAggregateIdentifier
    private UUID foodCartId;


    public OrderConfirmedEvent() {
    }

    public OrderConfirmedEvent(UUID foodCartId) {
        this.foodCartId = foodCartId;
    }
}
