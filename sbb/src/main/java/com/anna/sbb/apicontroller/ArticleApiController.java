package com.anna.sbb.apicontroller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/articles") 
// @RequestMapping : 이 @Controller의 기본 URL을 /api/articles로 설정함
@RequiredArgsConstructor
// @RequiredArgsConstructor : final 이나 @NotNull 필드에 대한 생성자를 자동으로 생성함.
@RestController
// @Controller와 @ResponseBody를 결합한 것과 같은 기능을 함. 
// HTTP 요청을 처리한 결과를 JSON or XML 형식으로 반환함.
public class ArticleApiController {

	private final ArticleService articleService;
	
	private final UserService userService;
	
	/* GET /api/articles : 페이징된 기사 목록 조회 */
	@GetMapping
	public ResponseEntity<Page<ArticleViewDto>> listArticles(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber) {
		Page<ArticleViewDto> articlePage = this.articleService.getArticleViewPage(pageNumber);
		return ResponseEntity.ok(articlePage);
	}

	/* GET /api/articles/test : 전체 기사 목록 조회 */
	@GetMapping({ "/test", "/test/" })
	public ResponseEntity<List<ArticleViewDto>> listAllArticles() {
		List<ArticleViewDto> articleViewDtoList = this.articleService.getAllArticleViewDtos();
		return ResponseEntity.ok(articleViewDtoList);
	}

	/* Get /api/articles/detail/{id} : articleID가 {id}인 기사 조회 */
	@GetMapping({ "/detail/{id}", "/detail/{id}/" })
	public ResponseEntity<ArticleViewDto> detailArticle(@PathVariable("id") Long id) {
		ArticleViewDto articleViewDto = this.articleService.getArticleViewDtoById(id);
		return ResponseEntity.ok(articleViewDto);
	}
	
	/* GET /api/articles : 새 기사 생성을 위한 객체 컨테이너를 조회 */
	@GetMapping({ "/create", "/create/" })
	public ResponseEntity<ArticleSubmitForm> createArticle(ArticleSubmitForm articleSubmitForm) {
		return ResponseEntity.ok(articleSubmitForm);
	}
	
	@GetMapping("/modify/{id}")
	public ResponseEntity<ArticleSubmitForm> modifyArticle(@PathVariable("id") Long articleID) {
		ArticleSubmitForm articleSubmitForm = this.articleService.getArticleSubmitFormById(articleID);
		return ResponseEntity.ok(articleSubmitForm);
	}
	
	// @RequestBody 애너테이션은 Post 메서드를 통해 JSON 형태로 보낸 데이터를 자동으로 객체로 변환해줌.
	/* POST /api/articles/create : JSON 데이터를 통해 새로운 기사를 생성 */
	@PostMapping("/create")
	public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleSubmitForm articleSubmitForm,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(articleSubmitForm);
		}
		ArticleViewDto createdArticle = this.articleService.createArticle(articleSubmitForm, "Anna", userService);
//		return ResponseEntity.ok(articleSubmitForm);
		return ResponseEntity.ok(createdArticle);
	}

	/* PUT /api/articles/modify/{id} : JSON 데이터를 통해 articleID가 {id}인 기사를 수정 */
	@PutMapping("/modify/{id}")
	public ResponseEntity<?> modifyArticle(@PathVariable("id") Long articleID,
			@Valid @RequestBody ArticleSubmitForm articleSubmitForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(articleSubmitForm);
		}
		ArticleViewDto modifiedArticle = this.articleService.updateArticle(articleID, articleSubmitForm);
		return ResponseEntity.ok(modifiedArticle);
	}

	/* DELETE /api/articles/delete/{id} : articleID가 {id}인 기사를 삭제 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long articleID) {
		this.articleService.deleteArticle(articleID);
		return ResponseEntity.noContent().build();
	}
}

