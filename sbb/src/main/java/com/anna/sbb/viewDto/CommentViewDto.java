package com.anna.sbb.viewDto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CommentViewDto {

	// *** Controller <=== Database ***

	private Long commentId;
	private String commentor;
	private String content;
	private Long articleId;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	@Builder
	public CommentViewDto(Long commentId, String commentor, String content, Long articleId,
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		this.commentId = commentId;
		this.commentor = commentor;
		this.content = content;
		this.articleId = articleId;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;

		// 로깅 추가
//		log.info(
//				"[CommentViewDto @Builder created] >> commentId={}, commentor={}, content={}, articleId={}, createdDate={}, lastModifiedDate={}",
//				commentId, commentor, content, articleId, createdDate, lastModifiedDate);
	}
}
