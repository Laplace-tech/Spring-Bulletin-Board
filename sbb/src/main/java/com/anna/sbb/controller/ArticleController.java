package com.anna.sbb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anna.sbb.dto.ArticleDto;
import com.anna.sbb.service.ArticleService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/articles") // 기본 URL 경로를 *** /article *** 로 설정함.
@RequiredArgsConstructor // final로 선언된 필드에 대해 자동으로 생성자를 지원
@RestController //
public class ArticleController {

	private final ArticleService articleService; // @Service 클래스에 대한 의존성을 빈에서 주입받음.

	@GetMapping("/")
	public void redirectToArticles(HttpServletResponse response) throws IOException {
		response.sendRedirect("/articles");
	}

	@GetMapping
	public ResponseEntity<List<ArticleDto>> findAllArticles() {
		List<ArticleDto> articleDtoList = this.articleService.findAllArticles();
		return ResponseEntity.ok(articleDtoList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ArticleDto> findArticleById(@PathVariable("id") long id) {
		ArticleDto resultArticleDto = this.articleService.findArticleById(id);
		return ResponseEntity.ok(resultArticleDto);
	}

	// ResponseEntity는 HTTP Response Body로 Article 객체 자체를 반환하고 Status code도 함께 설정
	// @RequestBody 애너테이션은 Post 메서드를 통해 JSON 형태로 보낸 데이터를 자동으로 ArticleDTO로 변환해줌.
	@PostMapping("/create")
	// ResponseEntity는 HTTP Response Body로 Article 객체 자체를 반환하고 Status code도 함께 설정
	// @RequestBody 애너테이션은 Post 메서드를 통해 JSON 형태로 보낸 데이터를 자동으로 ArticleDTO로 변환해줌.
	public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto) {
		ArticleDto savedArticle = this.articleService.createArticle(articleDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
	}

	@PutMapping("/modify/{id}")
	public ResponseEntity<ArticleDto> modifyArticle(@PathVariable("id") Long id, @RequestBody ArticleDto articleDto) {
		ArticleDto modifiedArticle = this.articleService.modifyArticle(id, articleDto);
		return ResponseEntity.ok(modifiedArticle);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
		this.articleService.deleteArticleById(id);
		return ResponseEntity.noContent().build();
	}
}
