package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "content is required.")
	@Size(max = 500, message = "Comment must not exceed 500 characters.")
	private String content;
	
}
