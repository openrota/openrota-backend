package com.shareNwork.repository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectFeedback;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.proxy.MailerProxy;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

    private static final Logger LOGGER = Logger.getLogger(ProjectRepository.class);

    @Inject
    EntityManager em;

    @RestClient
    MailerProxy mailerProxy;

    @Transactional
    public Project createOrUpdateProject(Project project) throws ParseException {
        if (project.id == null) {
            project.setCreatedAt(LocalDateTime.now());
            project.setStatus(ProjectStatus.PENDING);
            persist(project);
        }
        return em.merge(project);
    }

    public List<Project> getProjectsByResource(long id) {
        return listAll().stream().filter(project -> project.getEmployee() != null && project.getEmployee().id == id).collect(Collectors.toList());
    }

    public List<Project> getProjectsByRequestor(long id) {
        return listAll().stream().filter(project -> project.getProjectManager() != null && project.getProjectManager().id == id).collect(Collectors.toList());
    }

    @Transactional
    public Project completeProject(long projectId, String comments) throws ParseException {
        Project project = findById(projectId);
        ProjectFeedback projectFeedback = new ProjectFeedback();
        if (project != null) {

            project.setStatus(ProjectStatus.COMPLETED);
            projectFeedback.setCreationDate(LocalDateTime.now());
            projectFeedback.setProject(project);
            projectFeedback.setFeedback(comments);
            projectFeedback.persist();
            em.merge(project);
            Response response = mailerProxy.sendEmail(EmailData.builder()
                                                              .emailType(EmailType.PROJECT_COMPLETED.value())
                                                              .mailTo(project.getProjectManager().getEmailId())
                                                              .emailTemplateVariables(Map.of("projectId", String.valueOf(projectId))).build());
            LOGGER.info("openrota-mailer-service:" + response.getStatusInfo());
        } else {
            throw new NotFoundException();
        }
        return project;
    }
}
