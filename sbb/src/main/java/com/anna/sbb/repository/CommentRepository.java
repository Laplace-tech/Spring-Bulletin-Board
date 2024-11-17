package com.anna.sbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anna.sbb.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
}
