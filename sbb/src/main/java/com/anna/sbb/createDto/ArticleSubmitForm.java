package com.anna.sbb.createDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@Getter
//@Setter
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
	
	public void setId(Long id) {
		this.id = id;
		System.err.println("setter id : " + id);
	}
	
	public void setTitle(String title) {
		this.title = title;
		System.err.println("setter title : " + title);
	}

	public void setContent(String content) {
		this.content = content;
		System.err.println("setter content : " + content);
	}

	public Long getId() {
		System.err.println("getter id : " + id);
		return id;
	}

	public String getTitle() {
		System.err.println("getter title : " + title);
		return title;
	}

	public String getContent() {
		System.err.println("getter content : " + content);
		return content;
	}
	
}


