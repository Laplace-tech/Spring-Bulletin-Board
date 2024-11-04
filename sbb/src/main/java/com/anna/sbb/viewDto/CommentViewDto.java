package com.anna.sbb.viewDto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentViewDto {

	// *** Controller <=== Database ***
	
	private Long id;
	private String commentor;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	@Builder
	public CommentViewDto(Long id, String commentor, String title, String content, 
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.commentor = commentor;
		this.content = content;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

}
