package com.anna.sbb.domain;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Getter
@Entity
public class Article extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private SiteUser author;
	
	@Column(length = 150, nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
		
	/*
	 * 해당 Article Entity에 달린 Comment 리스트
	 * 
	 * 이 필드는 데이터베이스에 직접 컬럼으로 생성되지 않습니다. 대신, Comment 테이블에서 외래 키(question_id)를 통해
	 * Article 엔티티와 연결됩니다.
	 * 
	 * 새로운 Comment를 추가할 때, Article의 commentList에 자동으로 추가할 수 있음.
	 */

	// Comment 엔티티에 있는 article 필드에 의해 매핑됨.
	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	private List<Comment> commentList = new ArrayList<>();

	@Builder
	public Article(String title, String content, SiteUser author) {
		this.title = title;
		this.content = content;
		this.author = author;
		
		// 로깅
		log.info("[Article] >> @Builder Article (title:{}, content:{}, author:{}", title, content, author);
	}
	
	public Article updateArticle(String modifiedTitle, String modifiedContent) {
		
		// 로깅
		log.info("[updateArticle] >> title({} -> {}) , content ({} -> {})", title, modifiedTitle, content, modifiedContent);
		
		this.title = modifiedTitle;
		this.content = modifiedContent;
		return this;
	}

	
//	public Long getId() {
//		log.info("Article @Getter getId called. Current id : {}", id);
//		return id;
//	}
//
//	public SiteUser getAuthor() {
//		log.info("Article @Getter getAuthor called. Current author : {}", author);
//		return author;
//	}
//
//	public String getTitle() {
//		log.info("Article @Getter getTitle called. Current title : {}", title);
//		return title;
//	}
//
//	public String getContent() {
//		log.info("Article @Getter getContent called. Current content : {}", content);
//		return content;
//	}
//
//	public List<Comment> getCommentList() {
//		log.info("Article @Getter getCommentList called. Current commentList : {}", commentList);
//		return commentList;
//	}

}
