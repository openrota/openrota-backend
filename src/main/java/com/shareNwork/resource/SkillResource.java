package com.shareNwork.resource;

import com.shareNwork.domain.Skill;
import com.shareNwork.repository.SkillRepository;
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
public class SkillResource {
    private SkillRepository skillRepository;

    @Query("skill")
    @Description("Get all skills")
    public List<Skill> findAll() {
        return this.skillRepository.findAll().list();
    }

    @Query("skillsByPage")
    @Description("Get Kills with pagination")
    public List<Skill> getSkillsByPage(@Name("pageSize") int pageSize, @Name("pageOffset") int pageOffset) {
        return this.skillRepository.findAll().page(Page.of(pageOffset, pageSize)).list();
    }

    @Mutation
    @Description("Add a new skill")
    public Skill createSkill(Skill skill) throws ParseException {
        return this.skillRepository.createSkill(skill);
    }

}

