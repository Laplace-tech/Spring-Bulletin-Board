package com.anna.sbb.viewcontroller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/articles")
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

	private final ArticleService articleService;

	@GetMapping
	public String listArticlesByPage(@RequestParam(value = "page", defaultValue = "0") int pageNumber, Model model) {
		Page<ArticleViewDto> articlePage = this.articleService.readPage(pageNumber);
		model.addAttribute("articlePage", articlePage);
		return "article_list";
	}

	@GetMapping("/detail/{id}")
	public String viewArticleDetail(@PathVariable("id") Long articleID, Model model, CommentSubmitForm commentSubmitForm) {
		ArticleViewDto article = this.articleService.readArticleById(articleID);
		model.addAttribute("article", article);
		return "article_detail";
	}

	@GetMapping("/create")
	public String showCreateArticleForm(ArticleSubmitForm articleSubmitForm) {
		return "article_form";
	}

	@PostMapping("/create")
	public String createArticleForm(@Valid ArticleSubmitForm articleSubmitForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "article_form";
		}
		ArticleViewDto newArticle = this.articleService.createArticle(1L, articleSubmitForm);
		return "redirect:/articles/detail/" + newArticle.getId();
	}

	@GetMapping("/modify/{id}")
	public String showUpdateArticleForm(@PathVariable("id") Long articleID, ArticleSubmitForm articleSubmitForm) {
		ArticleViewDto previousArticle = this.articleService.readArticleById(articleID);
		articleSubmitForm.setId(previousArticle.getId());
		articleSubmitForm.setTitle(previousArticle.getTitle());
		articleSubmitForm.setContent(previousArticle.getContent());
		
		return "article_form";
	}

	@PatchMapping("/modify/{id}")
	public String updateArticleForm(@Valid ArticleSubmitForm articleSubmitForm, @PathVariable("id") Long articleID,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "article_form";
		}
		this.articleService.updateArticle(articleID, 1L, articleSubmitForm);
		return "redirect:/articles/detail/" + articleID;
	}

	@DeleteMapping("/delete/{id}")
	public String deleteArticle(@PathVariable("id") Long articleID) {
	    this.articleService.deleteArticle(articleID, 1L);  // ArticleService에서 삭제 처리
	    return "redirect:/articles";
	}
	
}
