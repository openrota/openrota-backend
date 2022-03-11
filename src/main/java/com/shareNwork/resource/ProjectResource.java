package com.shareNwork.resource;

import com.shareNwork.domain.Project;
import com.shareNwork.repository.ProjectRepository;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
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

    @Query("projectsByPage")
    @Description("Get projects with pagination")
    public List<Project> getProjectsByPage(@Name("pageSize") int pageSize, @Name("pageOffset") int pageOffset) {
        return this.projectRepository.findAll().page(Page.of(pageOffset, pageSize)).list();
    }

    @Mutation
    @Description("Create a new Employee")
    public Project createProject(Project project) throws ParseException {

        return this.projectRepository.createProject(project);
    }

}
