package com.anna.sbb.viewDto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class CommentViewDto {

	// *** Controller <=== Database ***

	private final Long commentId;
	private final String commentor;
	private final String content;
	private final Long articleId;
	private final LocalDateTime createdDate;
	private final LocalDateTime lastModifiedDate;

	public CommentViewDto(Long commentId, String commentor, String content, Long articleId, LocalDateTime createdDate,
			LocalDateTime lastModifiedDate) {
		this.commentId = commentId;
		this.commentor = commentor;
		this.content = content;
		this.articleId = articleId;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;

//		log.info("[CommentViewDto.@Builder] >> commentId=[{}], commentor=[{}], content=[{}], articleId=[{}], createdDate=[{}], lastModifiedDate=[{}]",
//				commentId, commentor, content, articleId, createdDate, lastModifiedDate);
	}
}
