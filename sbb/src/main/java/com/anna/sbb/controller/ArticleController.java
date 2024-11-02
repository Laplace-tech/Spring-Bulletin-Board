package com.anna.sbb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

	private final ArticleService articleService;
	
	@GetMapping("/")
	public void redirectToArticles(HttpServletResponse response) throws IOException {
		response.sendRedirect("/articles");
	}
	
	@GetMapping
	public ResponseEntity<List<ArticleViewDto>> findAllArticles() {
		List<ArticleViewDto> articles = this.articleService.findAllArticles();
		return ResponseEntity.ok(articles);
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<ArticleViewDto> findArticleById(@PathVariable("id") Long id) {
		ArticleViewDto articleViewDto = this.articleService.findArticleById(id);
		return ResponseEntity.ok(articleViewDto);
	}

	// ResponseEntity는 HTTP Response Body로 Article 객체 자체를 반환하고 Status code도 함께 설정
	// @RequestBody 애너테이션은 Post 메서드를 통해 JSON 형태로 보낸 데이터를 자동으로 ArticleDTO로 변환해줌.	
	@PostMapping("/create")
	public ResponseEntity<ArticleViewDto> createArticle(@RequestBody ArticleSubmitForm articleSubmitForm, Long id) {
		ArticleViewDto createdArticle = this.articleService.createArticle(articleSubmitForm);
		return ResponseEntity.ok(createdArticle);
	}
		
	@PutMapping("/modify/{id}")
	public ResponseEntity<ArticleViewDto> modifyArticle(@PathVariable("id") Long id, @RequestBody ArticleSubmitForm articleSubmitForm) {
		ArticleViewDto modifiedArticle = this.articleService.modifyArticle(id, articleSubmitForm);
		return ResponseEntity.ok(modifiedArticle);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
		this.articleService.deleteArticleById(id);
		return ResponseEntity.noContent().build();
	}

}
