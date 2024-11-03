package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "제목은 필수인데...")
	@Size(max = 150, message = "제목이 너무 길어요...(최대 150자)")
	private String title;
	
	@NotEmpty(message = "내용은 필수인데...")
	private String content;
	
}
