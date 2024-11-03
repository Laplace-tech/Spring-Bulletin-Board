package com.anna.sbb.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anna.sbb.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
	
	Page<Article> findAll(Pageable pageable);
	
}