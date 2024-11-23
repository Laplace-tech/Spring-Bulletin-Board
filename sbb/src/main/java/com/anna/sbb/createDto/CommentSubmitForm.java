package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class CommentSubmitForm {

	private Long id;
	
	@NotEmpty(message = "content is required.")
	@Size(max = 800, message = "Comment must not exceed 800 characters.")
	private String content;

	@Builder
	public CommentSubmitForm(Long id, String content) {
		this.id = id;
		this.content = content;
		log.info("[CommentSubmitForm @Builder created] >> id={}, content={}", id, content);
	}
	
//  직접 작성한 Getter & Setter
//	public void setContent(String content) {
//		log.info("CommentSubmitForm @Setter setContent called. Previous content={}, New content={}", this.content, content);
//		this.content = content;
//	}
//	
//	public String getContent() {
//		log.info("CommentSubmitForm @Getter getContent called. Current content={}", this.content);
//		return content;
//	}
}
