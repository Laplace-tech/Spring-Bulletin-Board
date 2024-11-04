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
import com.anna.sbb.viewDto.ArticleViewDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticleApiController {

	private final ArticleService articleService;

	/* GET /api/articles : 페이징된 기사 목록 조회 */
	@GetMapping("")
	public ResponseEntity<Page<ArticleViewDto>> listArticles(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber) {
		Page<ArticleViewDto> articlePage = this.articleService.readPage(pageNumber);
		return ResponseEntity.ok(articlePage);
	}

	/* GET /api/articles/test : 전체 기사 목록 조회 */
	@GetMapping({ "/test", "/test/" })
	public ResponseEntity<List<ArticleViewDto>> listAllArticles() {
		List<ArticleViewDto> articleViewDtoList = this.articleService.readAllArticles();
		return ResponseEntity.ok(articleViewDtoList);
	}

	/* Get /api/articles/detail/{id} : articleID가 {id}인 기사 조회 */
	@GetMapping({ "/detail/{id}", "/detail/{id}/" })
	public ResponseEntity<ArticleViewDto> detailArticleById(@PathVariable("id") Long id) {
		ArticleViewDto articleViewDto = this.articleService.readArticleById(id);
		return ResponseEntity.ok(articleViewDto);
	}
	
	/* GET /api/articles : 새 기사 생성을 위한 객체 컨테이너를 조회 */
	@GetMapping({ "/create", "/create/" })
	public ResponseEntity<ArticleSubmitForm> createArticle(ArticleSubmitForm articleSubmitForm) {
		return ResponseEntity.ok(articleSubmitForm);
	}
	
	// @RequestBody 애너테이션은 Post 메서드를 통해 JSON 형태로 보낸 데이터를 자동으로 객체로 변환해줌.
	/* POST /api/articles/create : JSON 데이터를 통해 새로운 기사를 생성 */
	@PostMapping("/create")
	public ResponseEntity<ArticleViewDto> createArticle(@RequestBody ArticleSubmitForm articleSubmitForm,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(null); // 또는 적절한 에러 응답 생성
		}
		ArticleViewDto createdArticle = this.articleService.createArticle(1L, articleSubmitForm);
		return ResponseEntity.ok(createdArticle);
	}

	/* PUT /api/articles/modify/{id} : JSON 데이터를 통해 articleID가 {id}인 기사를 수정 */
	@PutMapping("/modify/{id}")
	public ResponseEntity<ArticleViewDto> modifyArticle(@PathVariable("id") Long id,
			@RequestBody ArticleSubmitForm articleSubmitForm) {
		ArticleViewDto modifiedArticle = this.articleService.updateArticle(id, articleSubmitForm);
		return ResponseEntity.ok(modifiedArticle);
	}

	/* DELETE /api/articles/delete/{id} : articleID가 {id}인 기사를 삭제 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id) {
		this.articleService.deleteArticleById(id);
		return ResponseEntity.noContent().build();
	}
}
