package com.shareNwork.resource;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectExtension;
import com.shareNwork.domain.ProjectSkillsProficiency;
import com.shareNwork.domain.ResourceRequest;
import com.shareNwork.domain.ResourceRequestSkillsProficiency;
import com.shareNwork.domain.constants.ProjectStatus;
import com.shareNwork.domain.constants.RowAction;
import com.shareNwork.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;

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

    @Mutation
    @Description("Create Project extension request")
    public Project extendProject(ProjectExtension projectExtension) {
        return projectRepository.extendProject(projectExtension);
    }

    @Mutation
    @Description("Update Project extension request")
    public Project updateProjectExtension(ProjectExtension projectExtension) {
        return projectRepository.updateProjectExtension(projectExtension);
    }
}
