package com.example.cqrs.configuration;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

@Slf4j
public class SimpleEventProcessingErrorHandler implements ListenerInvocationErrorHandler {

    @Override
    public void onError(Exception e, EventMessage<?> eventMessage, EventListener eventListener) throws Exception {
        log.error("=========> EventListener [{}] failed to handle event", eventMessage);

        throw e;
    }
}
