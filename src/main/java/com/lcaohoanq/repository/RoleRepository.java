package com.lcaohoanq.repository;

import com.lcaohoanq.entity.Role;
import com.lcaohoanq.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(UserRoleEnum userRoleEnum);

}