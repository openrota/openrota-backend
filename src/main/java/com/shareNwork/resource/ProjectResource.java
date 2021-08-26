package com.shareNwork.resource;

import com.shareNwork.domain.EmployeeSkillProficiency;
import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectSkillProficiency;
import com.shareNwork.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.text.ParseException;
import java.util.List;

@AllArgsConstructor
@GraphQLApi
public class ProjectResource {

   private ProjectRepository projectRepository;

   @Query("project")
   @Description("Get all projects")
   public List<Project> findAll() {
      return this.projectRepository.findAll().list();
   }

   @Mutation
   @Description("Create a new Project")
   public Project updateOrCreateProject(Project project) throws ParseException {
      return this.projectRepository.updateOrCreateProject(project);
   }

   @Mutation
   @Description("Delete a Project")
   public Project deleteProject(Long id) throws ParseException {
      return this.projectRepository.deleteProject(id);
   }

   @Mutation
   @Description("Add skills to Project")
   public Project addSkillsToProject(Long id, List<ProjectSkillProficiency> projectSkillProficiencies) throws ParseException {
      return this.projectRepository.addSkillsToProject(id, projectSkillProficiencies);
   }

}
