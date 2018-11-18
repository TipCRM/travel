package com.travel.service.repository;

import com.travel.service.entity.SysException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionRepository extends JpaRepository<SysException, Long> {

}
