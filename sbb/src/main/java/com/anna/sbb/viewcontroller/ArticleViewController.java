package com.anna.sbb.viewcontroller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/articles")
@RequiredArgsConstructor
@Controller
public class ArticleViewController {

	private final ArticleService articleService;
	private final UserService userService;
	
	@GetMapping
	public String viewArticlesByPage(@RequestParam(value = "page", defaultValue = "0") int pageNum, Model model) {
		
		log.info("[GET: /articles?page={}] Fetching articles for page number.", pageNum);
		
		Page<ArticleViewDto> articlePage = this.articleService.getArticleViewPage(pageNum);
		model.addAttribute("articlePage", articlePage);
		
		log.info("[GET: /articles?page={}] Retrieved [{}] articles page [{}]", pageNum, articlePage.getContent().size(), pageNum);
		return "article_list";
	}

	@GetMapping("/detail/{id}")
	public String viewArticleDetail(@PathVariable("id") Long articleID, CommentSubmitForm commentSubmitForm,
			Model model) {

		log.info("[GET: /articles/detail/{}] Fetching article details with ID : [{}].", articleID, articleID);

		ArticleViewDto article = this.articleService.getArticleViewDtoById(articleID);
		model.addAttribute("article", article);

		log.info("[GET: /articles/detail/{}] Fetched article details with ID : [{}].", articleID, article.getId());
		return "article_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String viewCreateArticleForm(ArticleSubmitForm articleSubmitForm, Principal principal) {
		log.info("[GET: /articles/create] Loading article creation form for user : [{}]", principal.getName());
		return "article_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createArticleForm(@Valid ArticleSubmitForm articleSubmitForm,
			BindingResult bindingResult, Principal principal) {
		
		log.info("[POST: /articles/create] Validating form data submitted by user : [{}]", principal.getName());
		if (bindingResult.hasErrors()) {

			log.warn("[POST: /articles/create] Form validation failed for user : [{}]", principal.getName());
			log.warn("[POST: /articles/create] Errors : [{}]", bindingResult.getFieldError());

			return "article_form";
		}
		log.info("[POST: /articles/create] Validation check result : [{}]", !bindingResult.hasErrors());
		
		
		log.info("[POST: /articles/create] Creating new article for user : [{}]", principal.getName());
		ArticleViewDto newArticle = this.articleService.createArticle(articleSubmitForm, principal.getName(), userService);

		log.info("[POST: /articles/create] Article created Successfully! User : [{}], Article ID : [{}]",
				principal.getName(), newArticle.getId());
		return String.format("redirect:/articles/detail/%s", newArticle.getId());
	}

	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String viewUpdateArticleForm(@PathVariable("id") Long articleID, CommentSubmitForm commentSubmitForm,
			Model model, Principal principal) {

		log.info("[GET: /articles/modify/{}] Checking update permissions for user : [{}]", articleID, principal.getName());
		if (!ControllerUtils.hasPermission(articleID, principal, articleService::hasAuthorPermission)) {
			
			log.warn("[GET: /articles/modify/{}] Permission denied for user : [{}]", articleID, principal.getName());
			
			ArticleViewDto article = this.articleService.getArticleViewDtoById(articleID);

			model.addAttribute("article", article);
			model.addAttribute("error", "수정 권한이 없습니다");
			
			log.warn("[GET: /articles/modify/{}] Redirecting to article detail view with error message", articleID);
			return "article_detail";
		}

		
		log.info("[GET: /articles/modify/{}] Fetching data for update form.", articleID);
		
		ArticleSubmitForm editArticleSubmitForm = this.articleService.getArticleSubmitFormById(articleID);
		model.addAttribute("articleSubmitForm", editArticleSubmitForm);

		log.info("[GET: /articles/modify/{}] Update form loaded successfully for user : [{}]", 
				articleID, principal.getName());
		
		return "article_form"; // 수정 폼 페이지로 이동
	}

	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/modify/{id}")
	public String updateArticleForm(@Valid ArticleSubmitForm articleSubmitForm, @PathVariable("id") Long articleID,
			BindingResult bindingResult, Model model, Principal principal) {
		
		log.info("[PATCH: /articles/modify/{}] Checking update permission for user : [{}]", articleID, principal.getName());
		if (!ControllerUtils.hasPermission(articleID, principal, articleService::hasAuthorPermission)) {
			
			log.warn("[PATCH: /articles/modify/{}] Permission check failed for user : [{}]", articleID, principal.getName());
			
			ArticleViewDto article = this.articleService.getArticleViewDtoById(articleID);
			model.addAttribute("article", article);
			model.addAttribute("error", "수정 권한이 없습니다");
			
			log.warn("[PATCH: /articles/modify/{}] Returning article detail view with error message.", articleID);
			return "article_detail";
		}

		log.info("[PATCH: /articles/modify/{}] Validating form data submitted by user : [{}]", articleID, principal.getName());
		if (bindingResult.hasErrors()) {
			
			log.warn("[PATCH: /articles/modify/{}] Form validation failed for user : [{}]", articleID, principal.getName());
			log.warn("[PATCH: /articles/modify/{}] Errors : [{}]", articleID, bindingResult.getFieldError());

			log.warn("[PATCH: /articles/modify/{}] Returning article update form with error message", articleID);
			return "article_form";
		}
		log.info("[PATCH: /articles/modify/{}] Validation check result : [{}]", articleID, !bindingResult.hasErrors());
		
		
		log.info("[PATCH: /articles/modify/{}] Updating article for user : [{}]", articleID, principal.getName());
		ArticleViewDto modifiedArticle = this.articleService.updateArticle(articleID, articleSubmitForm);

		log.info("[PATCH: /articles/modify/{}] Successfully updated article! User : [{}], LastModified : [{}]",
				articleID, principal.getName(), modifiedArticle.getLastModifiedDate());
		
		return String.format("redirect:/articles/detail/%d", modifiedArticle.getId());
	}

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteArticle(@PathVariable("id") Long articleID, Principal principal) {

		log.info("[DELETE: /articles/delete/{}] Received request by user: [{}]", articleID, principal.getName());

		log.info("[DELETE: /articles/delete/{}] Checking delete permissions for user: [{}]", articleID,
				principal.getName());
		if (!ControllerUtils.hasPermission(articleID, principal, articleService::hasAuthorPermission)) {
			log.warn("[DELETE: /articles/delete/{}] >> Permission check failed for user: [{}]", articleID,
					principal.getName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
		}

		try {
			articleService.deleteArticle(articleID);
			log.info("[DELETE: /articles/delete/{}] Article ID: {} successfully deleted by user: [{}]", articleID,
					articleID, principal.getName());
			return ResponseEntity.ok("삭제되었습니다.");
		} catch (Exception e) {
			log.error("[DELETE: /articles/delete/{}] Error occurred while deleting article ID: [{}]. Error: [{}]",
					articleID, articleID, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
		}
	}
	
}
