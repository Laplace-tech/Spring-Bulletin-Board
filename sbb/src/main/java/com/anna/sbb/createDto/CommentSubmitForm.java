package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Data
public class CommentSubmitForm {

	@NotEmpty(message = "내용은 필수인데...")
	private String content;
	
}
