package com.anna.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anna.sbb.domain.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long>{
}
