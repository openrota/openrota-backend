package com.shareNwork.repository;

import com.shareNwork.domain.Project;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class ProjectRepository implements PanacheRepository<Project> {

    @Transactional
    public Project createProject(Project project) throws ParseException {
        this.persist(project);
        return project;
    }

}
