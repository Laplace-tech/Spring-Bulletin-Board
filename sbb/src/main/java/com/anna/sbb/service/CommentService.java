package com.anna.sbb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.ArticleMapper;
import com.anna.sbb.mapper.CommentMapper;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.repository.CommentRepository;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.CommentViewDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	private final ArticleMapper articleMapper;
	private final CommentMapper commentMapper;

	// *** Controller <=== Database ***
	// *** CommentViewDto <=== Comment ***
	@Deprecated
	public List<CommentViewDto> getAllComment() {
		return this.commentRepository.findAll().stream()
				.map(commentMapper::toViewDto).toList();
	}

	// *** Controller <=== Database ***
	// *** CommentViewDto <=== Comment ***
	public CommentViewDto getCommentById(Long id) {
		return this.commentRepository.findById(id).map(commentMapper::toViewDto)
				.orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException : " + id));
		
	}

	// *** Controller ===> Database ***
	// *** CommentSubmitForm ===> Comment ***
	@Transactional
	public CommentViewDto createArticle(CommentSubmitForm commentSubmitForm, Long articleID, Long userID) {
		
		/*********************************************************************************************/
		SiteUser siteUser = this.userRepository.findById(1L)
				.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userID));
		/*********************************************************************************************/	
		
		Article article = this.articleRepository.findById(articleID)
				.orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException : " + articleID));
		
		Comment newComment = this.commentMapper.toEntity(commentSubmitForm, article, siteUser);
		return this.commentMapper.toViewDto(commentRepository.save(newComment));
	}

	// *** Controller ===> Database ***
	// *** CommentSubmitForm ===> Comment
	@Transactional
	public CommentViewDto modifyComment(Long commentID, CommentSubmitForm modifiedForm) {
		return commentRepository.findById(commentID).map(commentEntity -> {
			commentEntity.update(modifiedForm.getContent());
			return commentRepository.save(commentEntity);
		}).map(commentMapper::toViewDto)
				.orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException : " + commentID));
	}

	// *** Controller ===> Database ***
	@Transactional
	public void deleteCommentById(Long commentID) {
		if (!this.commentRepository.existsById(commentID)) {
			throw new IllegalArgumentException("IllegalArgumentException : " + commentID);
		}
		this.commentRepository.deleteById(commentID);
	}

}
