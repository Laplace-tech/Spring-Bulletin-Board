package com.anna.sbb.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
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
		
		// 로깅
		log.info("[Comment] >> @Builder Comment (article:{}. commentor:{}, content:{}", article, commentor, content);
	}
	
	public Comment updateComment(String modifiedContent) {
		
		// 로깅
		log.info("[updateComment] >> content({} -> {})", content, modifiedContent);
		
		this.content = modifiedContent;
		return this;
	}
	

//	public Long getId() {
//		log.info("Comment @Getter getId called. Current id : {}", id);
//		return id;
//	}
//
//	public SiteUser getCommentor() {
//		log.info("Comment @Getter getCommentor called. Current commentor : {}", commentor);
//		return commentor;
//	}
//
//	public String getContent() {
//		log.info("Comment @Getter getContent called. Current content : {}", content);
//		return content;
//	}
//
//	public Article getArticle() {
//		log.info("Comment @Getter getArticle called. Current Article : {}", article);
//		return article;
//	}

}
