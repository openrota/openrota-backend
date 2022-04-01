package com.shareNwork.repository;

import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.ResourceRequestSkillsProficiency;
import com.shareNwork.domain.SharedResource;
import com.shareNwork.domain.constants.ResourceRequestStatus;
import com.shareNwork.domain.constants.RowAction;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ResourceRequestRepository implements PanacheRepository<ResourceRequest> {

    @Inject
    EntityManager em;

    @Transactional
    public ResourceRequest updateOrCreate(ResourceRequest shareResourceRequest) throws ParseException {
        if (shareResourceRequest.id == null) {
            shareResourceRequest.setCreatedAt(LocalDateTime.now());
            shareResourceRequest.setStartDate(LocalDateTime.now());
            shareResourceRequest.setEndDate(LocalDateTime.now());
            shareResourceRequest.setStatus(ResourceRequestStatus.PENDING);
            persist(shareResourceRequest);
            addSkillsToResourceRequests(shareResourceRequest.id, shareResourceRequest.getSkillProficiencies());
            return shareResourceRequest;
        } else {
            addSkillsToResourceRequests(shareResourceRequest.id, shareResourceRequest.getSkillProficiencies());
            return em.merge(shareResourceRequest);
        }
    }

    @Transactional
    public ResourceRequest handleActions(RowAction actionName, ResourceRequest resourceRequest) throws ParseException {
        ResourceRequest request = findById(resourceRequest.id);
        if (request != null) {
            if (actionName.equals(RowAction.APPROVE)) {
                // send an email here
                if(resourceRequest.getResource().id != null){
                    Optional<SharedResource> sharedResource = SharedResource.findByIdOptional(resourceRequest.getResource().id);
                    sharedResource.ifPresent(sharedResource1 -> request.setResource(sharedResource1));
                }
                request.setStatus(ResourceRequestStatus.COMPLETED);
            } else if (actionName.equals(RowAction.REJECT)) {
                // send an email here
                request.setStatus(ResourceRequestStatus.CANCELLED);
            }
            em.merge(request);
        } else {
            throw new NotFoundException();
        }
        return request;
    }


    @Transactional
    public List<ResourceRequestSkillsProficiency> getSkillsByRequestId(long id) {
        ResourceRequest resourceRequest = findById(id);
        List<ResourceRequestSkillsProficiency> response = new ArrayList<>();
        if (resourceRequest != null) {
            List<ResourceRequestSkillsProficiency> resourceRequestSkillsProficiencies = ResourceRequestSkillsProficiency.listAll();
            for (ResourceRequestSkillsProficiency resourceRequestSkillsProficiency : resourceRequestSkillsProficiencies) {
                if (resourceRequestSkillsProficiency.getResourceRequest().id.equals(id)) {
                    response.add(resourceRequestSkillsProficiency);
                }
            }
        }
        return response;
    }

    @Transactional
    public ResourceRequest addSkillsToResourceRequests(Long id, List<ResourceRequestSkillsProficiency> employeeSkillProficiencies) throws ParseException {
        ResourceRequest employee = findById(id);
        if (employee == null) {
            throw new NotFoundException();
        } else {
            if (employeeSkillProficiencies != null) {
                for (ResourceRequestSkillsProficiency employeeSkillProficiency : employeeSkillProficiencies) {
                    if (employeeSkillProficiency.id != null) {
                        updateSkillsOfEmployee(employeeSkillProficiency.id, employeeSkillProficiency);
                    } else {
                        employeeSkillProficiency.setResourceRequest(employee);
                        employeeSkillProficiency.persist();
                    }
                }
            }
        }
        return employee;
    }

    @Transactional
    public ResourceRequestSkillsProficiency updateSkillsOfEmployee(Long id, ResourceRequestSkillsProficiency employeeSkillProficiency) throws ParseException {
        ResourceRequestSkillsProficiency employeeSkillProficiency1 = ResourceRequestSkillsProficiency.findById(employeeSkillProficiency.id);
        if (employeeSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        if (employeeSkillProficiency.getSkill() != null) {
            employeeSkillProficiency1.setSkill(employeeSkillProficiency.getSkill());
        }
        if (employeeSkillProficiency.getProficiencyLevel() != null) {
            employeeSkillProficiency1.setProficiencyLevel(employeeSkillProficiency.getProficiencyLevel());
        }
        employeeSkillProficiency1.persist();
        return employeeSkillProficiency1;
    }
}
