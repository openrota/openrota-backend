package com.shareNwork.repository;

import com.shareNwork.domain.Role;
import com.shareNwork.domain.constants.RoleType;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {

    @Transactional
    public Set<Role> getRolesbyRoleTypes(Set<RoleType> roleTypes)  {
        return  listAll().stream().filter(roles1 -> roleTypes.stream().anyMatch(roleType -> roles1.getRoleName().equals(roleType))).collect(Collectors.toSet());
    }
}