package com.anna.sbb.mapper;

import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.CommentViewDto;

@Component
public class CommentMapper {

	// *** Controller ===> Database ***
	// *** Convert "CommentSubmitForm" to "Comment" *** 
	public Comment toEntity(CommentSubmitForm commentSubmitForm, Article article, SiteUser commentor) {
		return Comment.builder()
				.article(article)
				.commentor(commentor)
				.content(commentSubmitForm.getContent())
				.build();
	}
	
	// *** Controller <=== Database ***
	// *** Convert "Comment" to "CommentViewDto"
	public CommentViewDto toViewDto(Comment commentEntity) {
		return CommentViewDto.builder()
				.id(commentEntity.getId())
				.commentor(commentEntity.getCommentor().getUserName())
				.content(commentEntity.getContent())
				.createdDate(commentEntity.getCreatedDate())
				.lastModifiedDate(commentEntity.getLastModifiedDate())
				.build();
	}
	
}
