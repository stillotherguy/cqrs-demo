package com.example.cqrs.domain.command;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Ethan Zhang
 * @date 2022/5/1
 */
@Data
public class SelectProductCommand {

    @TargetAggregateIdentifier
    private UUID foodCartId;

    private UUID productId;

    private Integer quantity;

    public SelectProductCommand() {
    }

    public SelectProductCommand(UUID foodCartId, UUID productId, Integer quantity) {
        this.foodCartId = foodCartId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
