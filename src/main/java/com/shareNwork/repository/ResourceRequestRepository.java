package com.shareNwork.repository;

import com.shareNwork.domain.Employee;
import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.SharedResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

@ApplicationScoped
public class ResourceRequestRepository implements PanacheRepository<ResourceRequest> {
    @Inject
    EntityManager em;

    @Transactional
    public ResourceRequest createOrUpdateResource(ResourceRequest resourceRequest) throws ParseException {
        if(resourceRequest.id == null){
            persist(resourceRequest);
            return resourceRequest;
        } else {
            return em.merge(resourceRequest);
        }
    }

    @Transactional
    public ResourceRequest deleteResourceRequest(Long id) throws ParseException {
        ResourceRequest p = findById(id);
        if (p != null) {
            deleteById(id);
        }
        return p;
    }

}
