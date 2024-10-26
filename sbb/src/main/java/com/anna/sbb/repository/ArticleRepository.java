package com.anna.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.sbb.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{

}
