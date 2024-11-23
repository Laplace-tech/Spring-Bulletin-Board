package com.anna.sbb.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.CommentMapper;
import com.anna.sbb.repository.CommentRepository;
import com.anna.sbb.viewDto.CommentViewDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;
	
	// *** Helper Methods ***

	Comment findCommentById(Long commentID) {
		return this.commentRepository.findById(commentID)
			.orElseThrow(() -> new IllegalArgumentException(
					String.format("Comment with ID [%d] not found.", commentID)));
	}
	
	private Comment saveComment(Comment commentEntity) {
		return this.commentRepository.save(commentEntity);
	}
	
	// *** CRUD Operations ***
	
	public List<CommentViewDto> getAllCommentViewDtos() {
		
		log.info("[@CommentService.getAllCommentViewDtos] >> Fetching all comments as CommentViewDto.");
		
		return this.commentRepository.findAll().stream()
				.map(this.commentMapper::toViewDto)
				.toList();
	}
	
	public CommentViewDto getCommentViewDtoById(Long commentID) {
		
		log.info("[@CommentService.getCommentViewDtoById] >> Fetching CommentViewDto for Comment ID [{}].", commentID);
		
		Comment comment = this.findCommentById(commentID);
		return this.commentMapper.toViewDto(comment);
	}
	
	public CommentSubmitForm getCommentSubmitFormById(Long commentID) {
		
		log.info("[@CommentService.getCommentSubmitFormById] >> Fetching CommentSubmitForm for commentID [{}].", commentID);
		
		Comment comment = this.findCommentById(commentID);
		return this.commentMapper.toSubmitForm(comment);
	}
	
	public CommentViewDto createComment(String userName, Long articleID, CommentSubmitForm commentSubmitForm,
			UserService userService, ArticleService articleService) {
		
		log.info("[@CommentService.createComment] >> Creating new Comment for user [{}].", userName);
		
		SiteUser commentor = userService.findUserByUserName(userName);
		Article article = articleService.findArticleById(articleID);
		Comment newComment = this.commentMapper.toEntity(commentSubmitForm, article, commentor);

		newComment = this.saveComment(newComment);
		
		log.info("[@CommentService.createComment] >> Created New Comment with ID [{}].", newComment.getId());
		
		return this.commentMapper.toViewDto(newComment);
	}
	
	public CommentViewDto updateComment(Long commentID, CommentSubmitForm modifiedCommentForm) {
		
		log.info("[@CommentService.updateComment] >> Updating Comment with ID [{}].", commentID);
		
		Comment commentToUpdate = this.findCommentById(commentID);
		Comment modifiedComment = commentToUpdate.updateComment(modifiedCommentForm.getContent());
		
		modifiedComment = this.saveComment(modifiedComment);
		
		log.info("[@CommentService.updateComment] >> Updated Comment with ID [{}]", modifiedComment.getId());
		
		return this.commentMapper.toViewDto(modifiedComment);
	}
	
	public void deleteComment(Long commentID) {
		
		log.info("[@CommentService.deleteComment] >> Deleting Comment with ID [{}].", commentID);
		
		this.commentRepository.delete(this.findCommentById(commentID));
		
		log.info("[@CommentService.deleteComment] >> Deleted Comment with ID [{}].", commentID);
	}
	
	public boolean hasCommentorPermission(Long commentID, Principal principal) {
		
		log.info("[@CommentService.hasCommentorPermission] >> Checking commentor permission for Comment ID [{}] and  User [{}].", 
				commentID, principal.getName());
		
		String commentorName = this.findCommentById(commentID).getCommentor().getUserName();
		boolean hasPermission = principal.getName().equals(commentorName);
		
		log.info("[@CommentService.hasCommentorPermission] >> Permission check result : [{}].", hasPermission);
		
		return hasPermission;
	}
	
}
