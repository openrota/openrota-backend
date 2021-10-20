package com.shareNwork.repository;

import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.SharedResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class ResourceRequestRepository implements PanacheRepository<ResourceRequest> {

    @Inject
    EntityManager em;

    @Transactional
    public ResourceRequest updateOrCreate(ResourceRequest shareResourceRequest) throws ParseException {
        if (shareResourceRequest.id == null) {
            persist(shareResourceRequest);
            return shareResourceRequest;
        } else {
            return em.merge(shareResourceRequest);
        }
    }


}
