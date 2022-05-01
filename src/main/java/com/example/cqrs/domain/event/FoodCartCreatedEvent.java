package com.example.cqrs.domain.event;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Ethan Zhang
 * @date 2022/5/1
 */
@Data
public class FoodCartCreatedEvent {

    @TargetAggregateIdentifier
    private UUID foodCartId;


    public FoodCartCreatedEvent() {
    }

    public FoodCartCreatedEvent(UUID foodCartId) {
        this.foodCartId = foodCartId;
    }
}
