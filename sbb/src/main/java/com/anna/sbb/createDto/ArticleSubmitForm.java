package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class ArticleSubmitForm {

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
		log.info("ArticleSubmitForm @Builder called with id={}, title={}, content={}", id, title, content);
	}

//  직접 작성한 Getter & Setter
	public Long getId() {
		log.info("ArticleSubmitForm @Getter getId called. Current id={}", this.id);
		return id;
	}

	public void setId(Long id) {
		log.info("ArticleSubmitForm @Setter setId. Previous id={}, New id={}", this.id, id);
		this.id = id;
	}

	public String getTitle() {
		log.info("ArticleSubmitForm @Getter getTitle called. Current title={}", this.title);
		return title;
	}

	public void setTitle(String title) {
		log.info("ArticleSubmitForm @Setter setTitle called. Previous title={}, New title={}", this.title, title);
		this.title = title;
	}

	public String getContent() {
		log.info("ArticleSubmitForm @Getter getContent called. Current content={}", this.content);
		return content;
	}

	public void setContent(String content) {
		log.info("ArticleSubmitForm @Setter setContent called. Previous content='{}', New content='{}'", this.content,
				content);
		this.content = content;
	}
}
