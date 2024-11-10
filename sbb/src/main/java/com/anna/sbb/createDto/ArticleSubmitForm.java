package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Data
public class ArticleSubmitForm {

	// *** Controller ===> Database ***
	
	private Long id;
	
	@NotEmpty(message = "title is required")
	@Size(max = 60, message = "title must not exceed 60 characters.")
	private String title;
	
	@NotEmpty(message = "content is required")
	@Size(max = 2000, message = "content must not exceed 2000 characters.")
	private String content;

	@Builder
	public ArticleSubmitForm(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
}
