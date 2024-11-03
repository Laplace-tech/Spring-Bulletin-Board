package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentSubmitForm {

	@NotEmpty(message = "내용은 필수인데...")
	private String content;
	
}
