package com.anna.sbb.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, unique = true)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Lob
	@Column(nullable = false)
	private String content;

	// **생성자**: DTO가 아닌 개별 필드 값들을 받는 방식으로 변경
	@Builder
	public Article(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// **업데이트 메서드**: 개별 필드 업데이트
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
