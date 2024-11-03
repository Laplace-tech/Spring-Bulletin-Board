package com.anna.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.sbb.domain.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long>{

}
