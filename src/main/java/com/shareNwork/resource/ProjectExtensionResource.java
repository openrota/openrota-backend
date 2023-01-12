package com.shareNwork.resource;

import java.util.List;

import com.shareNwork.domain.Project;
import com.shareNwork.domain.ProjectExtension;
import com.shareNwork.repository.ProjectExtensionRepository;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

@AllArgsConstructor
@GraphQLApi
public class ProjectExtensionResource {

    public ProjectExtensionRepository projectExtensionRepository;

    @Mutation
    @Description("Create Project extension request")
    public Project extendProject(ProjectExtension projectExtension) {
        return this.projectExtensionRepository.extendProject(projectExtension);
    }

    @Mutation
    @Description("Update Project extension request")
    public Project updateProjectExtension(ProjectExtension projectExtension) {
        return this.projectExtensionRepository.updateProjectExtension(projectExtension);
    }

    @Query("getProjectExtensionByProjectId")
    @Description("Get ProjectExtension by Project id")
    public List<ProjectExtension> getProjectExtensionByProjectId(long projectId) {
        return this.projectExtensionRepository.getProjectExtensionByProjectId(projectId);
    }
}
