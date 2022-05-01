package com.example.cqrs.domain.command;

import java.util.UUID;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author Ethan Zhang
 * @date 2022/5/1
 */
@Data
public class ConfirmOrderCommand {

    @TargetAggregateIdentifier
    private UUID foodCartId;


    public ConfirmOrderCommand() {
    }

    public ConfirmOrderCommand(UUID foodCartId) {
        this.foodCartId = foodCartId;
    }
}
