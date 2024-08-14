package com.lcaohoanq.repository;

import com.lcaohoanq.entity.Status;
import com.lcaohoanq.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByStatusName(UserStatusEnum genderName);

    Status findById(int id);

}