package com.shareNwork.repository;

import com.shareNwork.domain.Skill;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.text.ParseException;

@ApplicationScoped
public class SkillRepository implements PanacheRepository<Skill> {

    @Transactional
    public Skill createSkill(Skill skill) throws ParseException {
        this.persist(skill);
        return skill;
    }
}