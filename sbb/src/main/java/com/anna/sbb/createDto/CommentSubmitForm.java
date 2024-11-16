package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommentSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "content is required.")
	@Size(max = 800, message = "Comment must not exceed 800 characters.")
	private String content;
	
}
