package com.shareNwork.service.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.shareNwork.service.EventBusService;
import io.quarkus.mailer.Mail;
import io.smallrye.mutiny.subscription.Cancellable;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EventBusServiceImpl implements EventBusService<Mail> {

    private static final Logger LOG = Logger.getLogger(EventBusServiceImpl.class);

    @Inject
    EventBus bus;

    @Override
    public void sendRequest(String eventName, Mail data) {
       bus.request(eventName, data)
               .subscribe()
               .with(t -> LOG.info("Event " + eventName + " sent successfully"),
                     e -> LOG.error("Failure occured sending event " + eventName + ": " + e.getMessage()));
    }
}
