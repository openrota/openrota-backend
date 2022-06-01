package com.shareNwork.repository;

import com.shareNwork.domain.AccessRequest;
import com.shareNwork.domain.constants.InvitationStatus;
import com.shareNwork.domain.constants.RoleType;
import com.shareNwork.domain.constants.RowAction;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.util.Set;

@ApplicationScoped
public class AccessRequestRepository implements PanacheRepository<AccessRequest> {

    @Inject
    EntityManager em;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    RoleRepository roleRepository;

    @Transactional
    public AccessRequest handleActions(RowAction actionName, AccessRequest accessRequest) throws ParseException {
        AccessRequest request = findById(accessRequest.id);
        if (request != null) {
            if (actionName.equals(RowAction.APPROVE)) {
                // send an email here
                request.setStatus(InvitationStatus.COMPLETED);
                employeeRepository.createEmployeeWithRole(request.getEmailId(), "", roleRepository.getRolesbyRoleTypes(Set.of(RoleType.REQUESTOR)));
            } else if (actionName.equals(RowAction.REJECT)) {
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
