package com.shareNwork.repository;

import com.shareNwork.domain.EmployeeSkillProficiency;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectSkillProficiency;
import com.shareNwork.domain.SharedResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.text.ParseException;
import java.util.List;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

   @Inject
   EntityManager em;

   @Transactional
   public Project createProject(Project project) throws ParseException {
      em.persist(project);
      return project;
   }

   @Transactional
   public Project deleteProject(Long id) throws ParseException {
      Project p = findById(id);
      if (p != null) {
         deleteById(id);
      }
      return p;
   }

   @Transactional
   public Project addSkillsToProject(Long id, List<ProjectSkillProficiency> projectSkillProficiencies) throws ParseException {
      Project project = findById(id);
      if (project == null) {
         throw new NotFoundException();
      } else {
         for (ProjectSkillProficiency projectSkillProficiency: projectSkillProficiencies) {
            projectSkillProficiency.setProject(project);
            projectSkillProficiency.persist();
         }
      }
      return project;
   }

}
