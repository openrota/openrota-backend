package com.shareNwork.repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.shareNwork.domain.EmailData;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectExtension;
import com.shareNwork.domain.constants.EmailType;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.proxy.MailerProxy;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProjectExtensionRepository implements PanacheRepository<ProjectExtension> {

    public static final Logger LOGGER = Logger.getLogger(ProjectExtensionRepository.class);

    @RestClient
    MailerProxy mailerProxy;

    @Transactional
    public Project extendProject(ProjectExtension projectExtension) {
        if (validateProjectExtensionIsNew(projectExtension)) {
            throw new RuntimeException("Project Extension Request is already pending!");
        }
        Project project = Project.findById(projectExtension.getProject().id);
        projectExtension.setProject(project);
        projectExtension.persist();
        project.setStatus(ProjectStatus.EXTENSION_REQUESTED);
        Response response = mailerProxy.sendEmail(EmailData.builder()
                                                          .emailType(EmailType.PROJECT_EXTENSION_REQ.value())
                                                          .mailTo(project.getProjectManager().getEmailId())
                                                          .emailTemplateVariables(Map.of("requestId", projectExtension.id,
                                                                                         "projectName", project.getProjectName()))
                                                          .build());
        LOGGER.info("openrota-mailer-service:" + response.getStatusInfo());
        return project;
    }

    @Transactional
    public Project updateProjectExtension(ProjectExtension projectExtension) {
        ProjectExtension projectExtensionToUpdate = ProjectExtension.findById(projectExtension.id);
        if (projectExtensionToUpdate == null) {
            throw new RuntimeException("Project Extension not found!");
        }
        if (ProjectStatus.EXTENSION_DENIED.equals(projectExtensionToUpdate.getStatus())) {
            throw new RuntimeException("Project Extension is already Denied!");
        }
        if (ProjectStatus.EXTENSION_APPROVED.equals(projectExtensionToUpdate.getStatus())) {
            throw new RuntimeException("Project Extension is already Denied!");
        }
        projectExtensionToUpdate.setStatus(projectExtension.getStatus());
        Project project = projectExtensionToUpdate.getProject();
        EmailData emailData = EmailData.builder().emailType(EmailType.PROJECT_EXTENSION_REQ_STATUS.value())
                .mailTo(project.getProjectManager().getEmailId()).build();
        if (ProjectStatus.EXTENSION_APPROVED.equals(projectExtension.getStatus())) {
            project.setEndDate(projectExtension.getExtendedDate());
            project.setStatus(ProjectStatus.INPROGRESS);
            emailData.setEmailTemplateVariables(Map.of("requestId", projectExtension.id,
                                                       "projectName", project.getProjectName(),
                                                       "endDate", project.getEndDate(),
                                                       "approved", "true"));
        } else if (ProjectStatus.EXTENSION_DENIED.equals(projectExtension.getStatus())) {
            project.setStatus(ProjectStatus.INPROGRESS);
            emailData.setEmailTemplateVariables(Map.of("requestId", projectExtension.id,
                                                       "projectName", project.getProjectName(),
                                                       "approved", "false"));
        }
        Response response = mailerProxy.sendEmail(emailData);
        LOGGER.info("openrota-mailer-service:" + response.getStatusInfo());
        return project;
    }

    public List<ProjectExtension> getProjectExtensionByProjectId(long projectId) {
        return listAll().stream().filter(p -> p.getProject().id == projectId).collect(Collectors.toList());
    }

    private boolean validateProjectExtensionIsNew(ProjectExtension projectExtension) {
        return list("status", ProjectStatus.EXTENSION_REQUESTED).stream()
                .anyMatch(p -> Objects.equals(p.getProject().id, projectExtension.getProject().id));
    }
}
