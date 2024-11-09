package com.anna.sbb.createDto;

import com.anna.sbb.viewDto.ArticleViewDto.ArticleViewDtoBuilder;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "title is required")
	@Size(max = 150, message = "title must not exceed 150 characters.")
	private String title;
	
	@NotEmpty(message = "content is required")
	@Size(max = 2000, message = "content must not exceed 2000 characters.")
	private String content;

}
