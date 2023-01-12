package com.shareNwork.resource;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.shareNwork.domain.CustomEvent;
import com.shareNwork.domain.constants.CustomEventType;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class CustomEventResource {

    @Inject
    EntityManager entityManager;

    @Query("event")
    @Description("Get all events")
    public List<CustomEvent> findAllEvents() {
        return CustomEvent.listAll();
    }

    @Query("eventTypes")
    @Description("Get all events")
    public CustomEventType[] findAllEventTypes() {
        return CustomEventType.values();
    }

    @Mutation
    @Description("Create or Update Events")
    @Transactional
    public CustomEvent createOrUpdateEvent(CustomEvent customEvent) throws ParseException {

        if (customEvent.id != null) {
            entityManager.merge(customEvent);
        } else {
            customEvent.persist();
        }
        return customEvent;
    }

    @Mutation
    @Description("Delete Events")
    @Transactional
    public boolean deleteEvent(long id) throws ParseException {
        return CustomEvent.deleteById(id);
    }
}
