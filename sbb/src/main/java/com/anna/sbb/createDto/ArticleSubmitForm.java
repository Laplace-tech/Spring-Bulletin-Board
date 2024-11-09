package com.anna.sbb.createDto;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleSubmitForm {

	// *** Controller ===> Database ***
	
	@Nullable
	private Long id;
	
	@NotEmpty(message = "title is required")
	@Size(max = 150, message = "title must not exceed 150 characters.")
	private String title;
	
	@NotEmpty(message = "content is required")
	@Size(max = 2000, message = "content must not exceed 2000 characters.")
	private String content;

}
