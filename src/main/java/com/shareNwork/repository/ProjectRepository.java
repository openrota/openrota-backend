package com.shareNwork.repository;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectFeedback;
import com.shareNwork.domain.ProjectSkillsProficiency;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.domain.constants.ResourceRequestStatus;
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

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

    @Inject
    EntityManager em;

    @Transactional
    public Project createOrUpdateProject(Project project) throws ParseException {
        if (project.id == null) {
            project.setCreatedAt(LocalDateTime.now());
            project.setStatus(ProjectStatus.PENDING);
            persist(project);
        }
//        addSkillsToProject(project.id, project.getSkillsProficiencies());
        return em.merge(project);
    }

    @Transactional
    public Project addSkillsToProject(Long id, List<ProjectSkillsProficiency> projectSkillProficiencies) throws ParseException {
        Project project = findById(id);
        if (project == null) {
            throw new NotFoundException();
        }
        else {
            if (projectSkillProficiencies != null) {
                for (ProjectSkillsProficiency projectSkillProficiency : projectSkillProficiencies) {
                    if (projectSkillProficiency.id != null) {
                        updateSkillsOfProject(projectSkillProficiency.id, projectSkillProficiency);
                    }
                    else {
                        projectSkillProficiency.setProject(project);
                        projectSkillProficiency.persist();
                    }
                }
            }
        }
        return project;
    }

    @Transactional
    public ProjectSkillsProficiency updateSkillsOfProject(Long id, ProjectSkillsProficiency projectSkillProficiency) throws ParseException {
        ProjectSkillsProficiency projectSkillProficiency1 = ProjectSkillsProficiency.findById(projectSkillProficiency.id);
        if (projectSkillProficiency1 == null) {
            throw new NotFoundException();
        }
        if (projectSkillProficiency.getSkill() != null) {
            projectSkillProficiency1.setSkill(projectSkillProficiency.getSkill());
        }
        if (projectSkillProficiency.getProficiencyLevel() != null) {
            projectSkillProficiency1.setProficiencyLevel(projectSkillProficiency.getProficiencyLevel());
        }
        projectSkillProficiency1.persist();
        return projectSkillProficiency1;
    }

    @Transactional
    public List<ProjectSkillsProficiency> getSkillsByProjectId(long id) {
        Project project = findById(id);
        List<ProjectSkillsProficiency> response = new ArrayList<>();
        if (project != null) {
            List<ProjectSkillsProficiency> projectSkillsProficiencies = ProjectSkillsProficiency.listAll();
            for (ProjectSkillsProficiency projectSkillsProficiency : projectSkillsProficiencies) {
                if (projectSkillsProficiency.getProject().id.equals(id)) {
                    response.add(projectSkillsProficiency);
                }
            }
        }
        return response;
    }

    @Transactional
    public Project getProjectByProjectId(long id) {
        Project project = findById(id);
//        if (project != null) {
//            List<ProjectSkillsProficiency> projectSkillsProficiencies = getSkillsByProjectId(id);
//            project.setSkillsProficiencies(projectSkillsProficiencies);
//        }
        return project;
    }

    @Transactional
    public Project completeProject(long projectId, String comments) throws ParseException {
        Project project = getProjectByProjectId(projectId);
        ProjectFeedback projectFeedback = new ProjectFeedback();
        if (project != null) {

            project.setStatus(ProjectStatus.COMPLETED);
            projectFeedback.setCreationDate(LocalDateTime.now());
            projectFeedback.setProject(project);
            projectFeedback.setFeedback(comments);
            projectFeedback.persist();
            em.merge(project);
        }
        else {
            throw new NotFoundException();
        }
        return project;
    }

}
