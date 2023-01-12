package com.shareNwork.resource;

import java.text.ParseException;
import java.util.List;

import javax.transaction.Transactional;

import com.shareNwork.domain.Project;
import com.shareNwork.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class ProjectResource {

    private ProjectRepository projectRepository;

    @Query("project")
    @Description("Get all projects")
    public List<Project> findAllProjects() {
        return this.projectRepository.findAll().list();
    }

    @Query("projectsByRequestor")
    @Description("Get projects by requestor")
    public List<Project> getProjectsByRequestor(long id) {
        return this.projectRepository.getProjectsByRequestor(id);
    }

    @Query("projectsByResource")
    @Description("Get projects by resource")
    public List<Project> getProjectsByResource(long id) {
        return this.projectRepository.getProjectsByResource(id);
    }

    @Query("getProjectById")
    @Description("Get project by id")
    @Transactional
    public Project getProjectById(long id) {
        return projectRepository.findById(id);
    }

    @Mutation
    @Description("Create or Update Project")
    public Project createOrUpdateProject(Project project) throws ParseException {

        return this.projectRepository.createOrUpdateProject(project);
    }

    @Mutation
    @Description("complete project")
    public com.shareNwork.domain.Project completeProject(long projectId, String comments) throws ParseException {
        return this.projectRepository.completeProject(projectId, comments);
    }
}
