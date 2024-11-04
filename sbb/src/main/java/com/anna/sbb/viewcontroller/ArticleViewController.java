package com.anna.sbb.viewcontroller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {
	
	private final ArticleService articleService;

	@GetMapping("/articles/")
	public void redirectToArticles(HttpServletResponse response) throws IOException {
		response.sendRedirect("/articles");
	}
	
	@GetMapping("/articles")
	public String listArticles(@RequestParam(value = "page", defaultValue = "0") int pageNumber, Model model) {
		Page<ArticleViewDto> articlePage = this.articleService.readPage(pageNumber);
		model.addAttribute("articlePage", articlePage);
		return "article_list";
	}

	@GetMapping("/articles/detail/{id}")
	public String findArticleById(@PathVariable("id") Long id, Model model) {
		ArticleViewDto articleViewDto = this.articleService.readArticleById(id);
		model.addAttribute("article", articleViewDto);
		return "article_detail";
	}

	@GetMapping("/articles/create")
	public String createArticle(ArticleSubmitForm articleSubmitForm) {
		return "article_form";
	}
	
	@PostMapping("/articles/create")
	public String createArticle(@Valid ArticleSubmitForm articleSubmitForm,
			BindingResult bindingResult) {
		ArticleViewDto newArticle = this.articleService.createArticle(1L, articleSubmitForm);
		return "redirect:/articles/detail/" + newArticle.getId();
	}
	
	
//	@PutMapping("/modify/{id}")
//	public ResponseEntity<ArticleViewDto> modifyArticle(@PathVariable("id") Long id,
//			@RequestBody ArticleSubmitForm articleSubmitForm) {
//		ArticleViewDto modifiedArticle = this.articleService.modifyArticle(id, articleSubmitForm);
//		return ResponseEntity.ok(modifiedArticle);
//	}
//
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
//		this.articleService.deleteArticleById(id);
//		return ResponseEntity.noContent().build();
//	}
}
