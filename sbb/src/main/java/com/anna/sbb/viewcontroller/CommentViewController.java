package com.anna.sbb.viewcontroller;

import java.security.Principal;

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

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.service.CommentService;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.CommentViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentViewController {

	private final CommentService commentService;
	private final ArticleService articleService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createCommentForm(@PathVariable("id") Long articleID, @Valid CommentSubmitForm commentSubmitForm, 
			BindingResult bindingResult, Principal principal) {
		
		log.info("[POST: /comment/create/{}] Validating form data submitted by user : [{}]", articleID, principal.getName());
		if(bindingResult.hasErrors()) {
			
			log.warn("[POST: /comment/create/{}] Form validation failed for user : [{}]", articleID, principal.getName());
			log.warn("[POST: /comment/create/{}] Errors : [{}]", articleID, bindingResult.getFieldError());

			return "article_form";
		}
		
		log.info("[POST: /comment/create/{}] Validation check result : [{}]", articleID, !bindingResult.hasErrors());		
		
		log.info("[POST: /comment/create/{}] Creating new comment for user : [{}]", articleID, principal.getName());
		CommentViewDto newComment = this.commentService.createComment(principal.getName(), articleID, commentSubmitForm,
				userService, articleService);

		log.info("[POST: /comment/create/{}] Comment created Successfully! User : [{}], Comment ID : [{}]",
				articleID, principal.getName(), newComment.getCommentId());
		return String.format("redirect:/articles/detail/%s#answer_%s", articleID, newComment.getCommentId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String viewUpdateCommentForm(@PathVariable("id") Long commentID, CommentSubmitForm commentSubmitForm,
			Model model, Principal principal) {

		log.info("[GET: /comment/modify/{}] Checking update permissions for user : [{}]", commentID, principal.getName());
		if(!ControllerUtils.hasPermission(commentID, principal, commentService::hasCommentorPermission)) {
			
			log.warn("[GET: /comment/modify/{}] Permission denied for user : [{}]", commentID, principal.getName());
			
			ArticleViewDto articleViewDto = this.articleService.getArticleViewDtoByComment(commentID, this.commentService);
			
			model.addAttribute("article", articleViewDto);
			model.addAttribute("error", "수정 권한이 없습니다");
			
			return "article_detail";
		}
		
		log.info("[GET: /comment/modify/{}] Fetching data for update form.", commentID);
		
		CommentSubmitForm editCommentSubmitForm = this.commentService.getCommentSubmitFormById(commentID);
		model.addAttribute("commentSubmitForm", editCommentSubmitForm);
		
		log.info("[GET: /comment/modify/{}] Update form loaded successfully for user : [{}]", commentID, principal.getName());
		
		return "comment_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/modify/{id}")
	public String modifyComment(@PathVariable("id") Long commentID, @Valid CommentSubmitForm commentSumbmitForm,
			BindingResult bindingResult, Model model, Principal principal) {

		log.info("[PATCH: /comment/modify/{}] Checking update permission for user : [{}]", commentID,
				principal.getName());
		if (!ControllerUtils.hasPermission(commentID, principal, commentService::hasCommentorPermission)) {
			log.warn("[PATCH: /comment/modify/{}] Permission check failed for user : [{}]", commentID,principal.getName());

			ArticleViewDto article = this.articleService.getArticleViewDtoByComment(commentID, commentService);
			model.addAttribute("article", article);
			model.addAttribute("error", "수정 권한이 없습니다");

			log.warn("[PATCH: /comment/modify/{}] Returning article detail view with error message.", commentID);
			return "article_detail";
		}

		// 폼 데이터 유효성 검사
		log.info("[PATCH: /comment/modify/{}] Validating form data submitted by user : [{}]", commentID,
				principal.getName());
		if (bindingResult.hasErrors()) {
			log.warn("[PATCH: /comment/modify/{}] Form validation failed for user : [{}]", commentID, principal.getName());
			log.warn("[PATCH: /comment/modify/{}] Errors : [{}]", commentID, bindingResult.getFieldError());
			log.warn("[PATCH: /comment/modify/{}] Returning commenmt update form with error message", commentID);

			return "comment_form";
		}

		// 유효성 검사 통과
		log.info("[PATCH: /comment/modify/{}] Validation check result : [{}]", commentID, !bindingResult.hasErrors());

		// 수정 로직 수행
		log.info("[PATCH: /comment/modify/{}] Updating comment for user : [{}]", commentID, principal.getName());
		CommentViewDto modifiedComment = this.commentService.updateComment(commentID, commentSumbmitForm);

		log.info("[PATCH: /comment/modify/{}] Successfully updated comment! User : [{}], LastModified : [{}]",
				commentID, principal.getName(), modifiedComment.getLastModifiedDate());

		return String.format("redirect:/articles/detail/%s#answer_%s", modifiedComment.getArticleId(), commentID);
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentID, Principal principal) {

		log.info("[DELETE: /comment/delete/{}] Received request by user: [{}]", commentID, principal.getName());

		log.info("[DELETE: /comment/delete/{}] Checking delete permissions for user: [{}]", commentID,
				principal.getName());
		if (!ControllerUtils.hasPermission(commentID, principal, commentService::hasCommentorPermission)) {
			log.warn("[DELETE: /comment/delete/{}] >> Permission check failed for user: [{}]", commentID,
					principal.getName());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
		}

		try {
			commentService.deleteComment(commentID);
			log.info("[DELETE: /comment/delete/{}] Comment ID: {} successfully deleted by user: [{}]", commentID,
					commentID, principal.getName());
			return ResponseEntity.ok("삭제되었습니다.");
		} catch (Exception e) {
			log.error("[DELETE: /comment/delete/{}] Error occurred while deleting Comment ID: [{}]. Error: [{}]",
					commentID, commentID, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
		}
	}
}
