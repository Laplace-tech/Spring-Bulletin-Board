package com.anna.sbb.viewDto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ArticleViewDto {

	private Long id;
	private String author;
	private String title;
	private String content;
	private List<CommentViewDto> commentList;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticeViewDto"
	@Builder
	public ArticleViewDto(Long id, String author, String title, String content, List<CommentViewDto> commentList,
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.content = content;
		this.commentList = commentList;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;

		// 로깅
//		log.info(
//				"[ArticleViewDto: @Builder created] >> id={}, author={}, title={},"
//						+ " content={}, commentList={}, createdDate={}, lastModifiedDate={}",
//				id, author, title, content, commentList, createdDate, lastModifiedDate);
	}

}
