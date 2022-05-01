package com.example.cqrs.query;

import com.example.cqrs.domain.FindFoodCartQuery;
import com.example.cqrs.domain.event.FoodCartCreatedEvent;
import com.example.cqrs.domain.event.ProductDeselectedEvent;
import com.example.cqrs.domain.event.ProductSelectedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
class FoodCartProjector {

    private final FoodCartViewRepository foodCartViewRepository;

    public FoodCartProjector(FoodCartViewRepository foodCartViewRepository) {
        this.foodCartViewRepository = foodCartViewRepository;
    }

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("EventHandler received FoodCartCreatedEvent");

        log.info(Thread.currentThread().getName());

        FoodCartView foodCartView = new FoodCartView(event.getFoodCartId(), Collections.emptyMap());
        foodCartViewRepository.save(foodCartView);

        log.info("EventHandler processed FoodCartCreatedEvent");
    }

    @EventHandler
    public void on(ProductSelectedEvent event) {
        log.info("EventHandler received ProductSelectedEvent");

        throw new RuntimeException();

       /* foodCartViewRepository.findById(event.getFoodCartId()).ifPresent(
                foodCartView -> foodCartView.addProducts(event.getProductId(), event.getQuantity())
        );*/
    }

    @EventHandler
    public void on(ProductDeselectedEvent event) {
        foodCartViewRepository.findById(event.getFoodCartId()).ifPresent(
                foodCartView -> foodCartView.removeProducts(event.getProductId(), event.getQuantity())
        );
    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery query) {
        return foodCartViewRepository.findById(query.getFoodCartId()).orElse(null);
    }
}
