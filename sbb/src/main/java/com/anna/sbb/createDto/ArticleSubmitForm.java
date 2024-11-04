package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "title is required")
	@Size(max = 150, message = "title must not exceed 150 characters.")
	private String title;
	
	@NotEmpty(message = "content is required")
	@Size(max = 2000, message = "content must not exceed 2000 characters.")
	private String content;
	
}
