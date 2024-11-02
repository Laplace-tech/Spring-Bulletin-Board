package com.anna.sbb.viewDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleViewDto {

	private Long id;
	private SiteUser author;
	private String title;
	private String content;
	private List<Comment> commentList;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;

	// *** Controller <=== Database ***
	@Builder
	public ArticleViewDto(Long id, SiteUser author, String title, String content, List<Comment> commentList,
			LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.content = content;
		this.commentList = commentList;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

}
