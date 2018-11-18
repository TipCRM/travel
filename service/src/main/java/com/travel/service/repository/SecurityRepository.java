package com.travel.service.repository;

import com.travel.service.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {

    Security findByUserId(Long userId);
}
