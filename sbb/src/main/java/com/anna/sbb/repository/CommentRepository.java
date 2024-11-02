package com.anna.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.sbb.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
