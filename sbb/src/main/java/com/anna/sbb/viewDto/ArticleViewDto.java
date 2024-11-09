package com.anna.sbb.viewDto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleViewDto {

	// *** Controller <=== Database ***
	
	private Long id;
	private String author;
	private String title;
	private String content;
	private List<CommentViewDto> commentList;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	// *** Controller <=== Database ***
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
	}

}
