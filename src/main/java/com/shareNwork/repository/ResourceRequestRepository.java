package com.shareNwork.repository;

import com.shareNwork.domain.*;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.domain.constants.ResourceRequestStatus;
import com.shareNwork.domain.constants.RowAction;
import com.shareNwork.domain.constants.SkillProficiencyLevel;
import com.shareNwork.rest.roaster.RoasterService;
import com.shareNwork.proxy.MailerProxy;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResourceRequestRepository implements PanacheRepository<ResourceRequest> {

    @Inject
    EntityManager em;

    @Inject
    RoasterService roasterService;

    @RestClient
    MailerProxy mailerProxy;

    @Transactional
    public List<ResourceRequest> getSharedResourceByRequestor(long requestorId) {
        return listAll().stream().filter(resourceRequest -> resourceRequest.getRequester().id.equals(requestorId)).collect(Collectors.toList());
    }
    @Transactional
    public ResourceRequest updateOrCreate(ResourceRequest shareResourceRequest) throws ParseException {
        if (shareResourceRequest.id == null) {
            shareResourceRequest.setCreatedAt(LocalDateTime.now());
            shareResourceRequest.setStatus(ResourceRequestStatus.PENDING);
            Employee requestor = Employee.findById(shareResourceRequest.getRequester().id);
            if (requestor != null) {
                shareResourceRequest.setRequester(requestor);
            }
            persist(shareResourceRequest);
            roasterService.solveRoster(12);

        }
        mailerProxy.sendEmail(new EmailData(EmailType.NEW_RESOURCE_REQ.value(),
                                            shareResourceRequest.getRequester().getEmailId(),
                                            Map.of("requestId", String.valueOf(shareResourceRequest.id),
                                                   "projectName", shareResourceRequest.getProject())));
        return em.merge(shareResourceRequest);
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
                convertResourceRequestToProject(request);
                mailerProxy.sendEmail(new EmailData(EmailType.RESOURCE_REQUEST_STATUS.value(),
                                                    request.getRequester().getEmailId(),
                                                    Map.of("approved", String.valueOf(actionName.equals(RowAction.APPROVE)),
                                                           "resourceName", request.getResource().getEmailId())));
            } else if (actionName.equals(RowAction.REJECT)) {
                // send an email here
                request.setStatus(ResourceRequestStatus.CANCELLED);
                mailerProxy.sendEmail(new EmailData(EmailType.RESOURCE_REQUEST_STATUS.value(),
                                                    request.getRequester().getEmailId(),
                                                    Map.of("approved", String.valueOf(actionName.equals(RowAction.APPROVE)))));
            }
            em.merge(request);
        } else {
            throw new NotFoundException();
        }
        return request;
    }

    public void convertResourceRequestToProject(ResourceRequest resourceRequest){
        Project project = new Project();
        project.setProjectName(resourceRequest.getProject());
        project.setCreatedAt(LocalDateTime.now());
        project.setResourcerequest(resourceRequest);
        project.setEmployee(resourceRequest.getResource());
        project.setStatus(ProjectStatus.YET_TO_START);
        project.setBusinessUnit(resourceRequest.getBusinessUnit());
        String startDate = resourceRequest.getStartDate();
        String endDate = resourceRequest.getEndDate();
        Slot projectSlot = new Slot(startDate, endDate);
        projectSlot.persist();
        project.setSlot(projectSlot);
        project.setProjectManager(resourceRequest.getRequester());
        project.persist();
    }
}
