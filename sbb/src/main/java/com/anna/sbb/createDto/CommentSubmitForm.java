package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentSubmitForm {

	// *** Controller ===> Database ***
	
	@NotEmpty(message = "content is required.")
	@Size(max = 800, message = "Comment must not exceed 500 characters.")
	private String content;
	
}
