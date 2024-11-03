package com.anna.sbb.domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "commentor_id", nullable = false)
	private SiteUser commentor;

	@Lob
	@Column(nullable = false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;
	
	@Builder
	public Comment(Article article, SiteUser commentor, String content) {
		this.article = article;
		this.commentor = commentor;
		this.content = content;
	}
	
	public void update(String newContent) {
		this.content = newContent;
	}
}
