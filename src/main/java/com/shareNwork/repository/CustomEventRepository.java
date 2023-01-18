package com.shareNwork.repository;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.shareNwork.domain.CustomEvent;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CustomEventRepository implements PanacheRepository<CustomEvent> {

    private static final Logger LOGGER = Logger.getLogger(CustomEventRepository.class);

    @Inject
    EntityManager em;

    @Transactional
    public CustomEvent createOrUpdateEvents(CustomEvent customEvent) throws ParseException {
        if (customEvent.id != null) {
            em.merge(customEvent);
        } else {
            customEvent.persist();
        }
        return customEvent;
    }

    public List<CustomEvent> getEventsByResource(long id) {
        return listAll().stream().filter(event -> event.getEmployee() != null && event.getEmployee().id == id).collect(Collectors.toList());
    }
}
