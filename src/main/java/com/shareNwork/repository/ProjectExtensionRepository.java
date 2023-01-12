package com.shareNwork.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectExtension;
import com.shareNwork.domain.constants.ProjectStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProjectExtensionRepository implements PanacheRepository<ProjectExtension> {

    @Transactional
    public Project extendProject(ProjectExtension projectExtension) {
        if (!validateProjectExtensionIsNew(projectExtension)) {
            throw new RuntimeException("Project Extension Request is already pending!");
        }
        Project project = Project.findById(projectExtension.getProject().id);
        projectExtension.setProject(project);
        persist(projectExtension);
        project.setStatus(ProjectStatus.EXTENSION_REQUESTED);
        project.persist();
        return project;
    }

    @Transactional
    public Project updateProjectExtension(ProjectExtension projectExtension) {
        ProjectExtension projectExtensionToUpdate = ProjectExtension.findById(projectExtension.id);
        if (projectExtensionToUpdate == null) {
            throw new RuntimeException("Project Extension not found!");
        }
        projectExtensionToUpdate.setStatus(projectExtension.getStatus());
        Project project = Project.findById(projectExtension.getProject().id);
        if (ProjectStatus.EXTENSION_APPROVED.equals(projectExtension.getStatus())) {
            project.setEndDate(projectExtension.getExtendedDate());
            project.setStatus(ProjectStatus.INPROGRESS);
        } else if (ProjectStatus.EXTENSION_DENIED.equals(projectExtension.getStatus())) {
            project.setStatus(ProjectStatus.INPROGRESS);
        }
        return project;
    }

    public List<ProjectExtension> getProjectExtensionByProjectId(long projectId) {
        return listAll().stream().filter(p -> p.getProject().id == projectId).collect(Collectors.toList());
    }

    private boolean validateProjectExtensionIsNew(ProjectExtension projectExtension) {
        return listAll().stream().filter(p -> p.getProject().id == projectExtension.getProject().id
                && ProjectStatus.EXTENSION_REQUESTED.equals(p.getStatus())).collect(Collectors.toList()).isEmpty();
    }
}
