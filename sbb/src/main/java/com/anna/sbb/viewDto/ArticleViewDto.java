package com.anna.sbb.viewDto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class ArticleViewDto {

	private final Long id;
	private final String author;
	private final String title;
	private final String content;
	private final List<CommentViewDto> commentList;
	private final LocalDateTime createdDate;
	private final LocalDateTime lastModifiedDate;

	// Constructor for Builder (@Lombok handles this automatically)
	// Lombok's @Builder annotation provides the fluent builder API.
	// Logs are only enabled if necessary for debugging.
	public ArticleViewDto(Long id, String author, String title, String content, List<CommentViewDto> commentList,
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.content = content;
		this.commentList = commentList;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;

//		log.info("[ArticleViewDto.@Builder] >> id=[{}], author=[{}], title=[{}], content=[{}], commentList=[{}], createdDate=[{}], lastModifiedDate=[{}]",
//				id, author, title, content, commentList, createdDate, lastModifiedDate);

	}

}
