package com.anna.sbb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.CommentMapper;
import com.anna.sbb.repository.CommentRepository;
import com.anna.sbb.viewDto.CommentViewDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final ArticleService articleService;
	
	private final UserService userService;
	
	private final CommentMapper commentMapper;

	Comment findCommentById(Long commentID) {
		return this.commentRepository.findById(commentID)
				.orElseThrow(() -> new IllegalArgumentException("Comment Not Found with ID " + commentID));
	}
	
	public List<CommentViewDto> readAllComment() {
		return this.commentRepository.findAll().stream()
				.map(commentMapper::toViewDto)
				.toList();
	}

	public CommentViewDto readCommentById(Long commentID) {
		return this.commentMapper.toViewDto(findCommentById(commentID)); 
	}

	@Transactional
	public CommentViewDto createComment(Long articleID, Long userID, CommentSubmitForm commentSubmitForm) {
		Article article = this.articleService.findArticleById(articleID);
		SiteUser commentor = this.userService.findUserById(userID);
		Comment newComment = this.commentMapper.toEntity(commentSubmitForm, article, commentor);
		
		return this.commentMapper.toViewDto(commentRepository.save(newComment));
	}

	@Transactional
	public CommentViewDto modifyComment(Long commentID, CommentSubmitForm modifiedCommentForm) {
		Comment modifiedComment = this.findCommentById(commentID)
				.update(modifiedCommentForm.getContent());
	    this.commentRepository.save(modifiedComment);
		return commentMapper.toViewDto(modifiedComment);
	}

	@Transactional
	public void deleteComment(Long commentID) {
		this.commentRepository.delete(findCommentById(commentID));
	}

}
