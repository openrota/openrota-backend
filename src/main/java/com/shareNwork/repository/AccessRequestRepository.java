package com.shareNwork.repository;

import com.shareNwork.domain.AccessRequest;
import com.shareNwork.domain.Employee;
import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.constants.InvitationStatus;
import com.shareNwork.domain.constants.ResourceRequestStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;

@ApplicationScoped

public class AccessRequestRepository implements PanacheRepository<AccessRequest> {

    @Inject
    EntityManager em;

    @Transactional
    public AccessRequest handleActions(String actionName, AccessRequest accessRequest) throws ParseException {
        AccessRequest request = findById(accessRequest.id);
        if (request != null) {
            if (actionName.equals("approve")) {
                // send an email here
                request.setStatus(InvitationStatus.COMPLETED);
            } else if (actionName.equals("reject")) {
                // send an email here
                request.setStatus(InvitationStatus.REJECTED);
            }
            em.merge(request);
        } else {
            throw new NotFoundException();
        }
        return request;
    }
}
