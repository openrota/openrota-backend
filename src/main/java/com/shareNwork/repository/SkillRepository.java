package com.shareNwork.repository;

import java.text.ParseException;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.shareNwork.domain.Skill;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SkillRepository implements PanacheRepository<Skill> {

    @Transactional
    public Skill createSkill(Skill skill) throws ParseException {
        this.persist(skill);
        return skill;
    }
}