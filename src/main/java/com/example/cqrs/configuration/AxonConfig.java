package com.example.cqrs.configuration;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AxonConfig {

    @Bean
    public ListenerInvocationErrorHandler contractEventProcessingErrorHandler() {
        return new SimpleEventProcessingErrorHandler();
    }

    @Autowired
    public void configure(EventProcessingConfiguration configuration, CommandBus commandBus, @Qualifier("eventBus") EventBus eventBus) {
        configuration.usingTrackingProcessors(c -> c.getComponent(TrackingEventProcessorConfiguration.class,
                                                                  () -> TrackingEventProcessorConfiguration.forParallelProcessing(2))
                                                    .andInitialSegmentsCount(4)
                                                    .andBatchSize(20));

    }

}
