package com.anna.sbb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleDto {

	private Long id;
	private String title;
	private String content;

	// *** Controller <=== Datebase ***
	@Builder
	public ArticleDto(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}


}
