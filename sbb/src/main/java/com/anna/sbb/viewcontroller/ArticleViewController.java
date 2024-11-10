package com.anna.sbb.viewcontroller;

import org.springframework.data.domain.Page;
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
import com.anna.sbb.domain.Article;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/articles")
/* @RequestMapping : HTTP 요청과 이를 처리할 메서드를 매핑하는 데 사용됨.
 * @RequestMapping을 클래스 레벨에 적용하면, 해당 클래스의 모든 메서드에서 /articles로 시작하는 경로를 처리함.
 */
@RequiredArgsConstructor
/* @RequiredArgsConstructor : 클래스 내에서 final 필드나, @NonNull이 붙은 필드에 대해
 * 생성자가 자동으로 생성됨. 필드에 대한 의존성 주입을 자동으로 처리할 수 있음. @Autowired를 사용하지 않아도 됨.
 */
@Controller
/* @Controller는 Spring MVC(Model-View-Controller)에서 컨트롤러 클래스를 선언하는 데 사용되는 애너테이션.
 * @Controller는 웹 리퀘스트를 처리하고 모델에 객체 데이터를 입혀서(추가해서) 뷰를 반환하는 역할을 함.
 * 주로 웹 애플리케이션에서 HTTP 요청을 처리하는 클래스에 사용되며, 리턴타입이 뷰이름인 경우에 사용함.
 * @Controller가 붙은 클래스는 Spring의 DispatcherServlet이 클라이언트로 부터 요청을 처리할 때,
 * 해당 클래스를 컨트롤러로 인식하게 됨.
 */
public class ArticleViewController {

	// 의존성 주입
	private final ArticleService articleService;

//	public ArticleViewController(ArticleService articleService) {
//		this.articleService = articleService;
//	}
	
	@GetMapping // @RequestMapping(method = RequestMethod.GET)와 동일
	public String viewArticlesByPage(@RequestParam(value = "page", defaultValue = "0") int pageNum, Model model) {
		/* @RequestParam : HTTP 요청 URL에서 파라메터 값을 메서드 인자로 바인딩하는 애너테이션.
		 * value = "page" : HTTP 요청의 파라메터 이름이 **page**인 값을 가져오겠다는 의미임.
		 * defaultValue = "0" : page 파라메터가 포함되어있지 않으면 기본값으로 0을 사용함. 
		 *  즉, /articles 라고 리퀘스트를 보내면 /articles?page=0 으로 자동 설정.
		 *  
		 *  Model은 스프링 MVC에서 뷰(View)로 전달할 객체 데이터를 담는 객체임.
		 *  컨트롤러에서 model.addAttribute를 통해 넘길 데이터를 준비하고 이를 뷰로 전달
		 */
		Page<ArticleViewDto> articlePage = this.articleService.readArticleViewPage(pageNum);
		model.addAttribute("articlePage", articlePage);
		return "article_list";
	}

	@GetMapping("/detail/{id}")
	/* /detail/{id}는 URL 경로에서 id 값을 동적으로 받겠다는 의미.
	 * {id}부분은 **경로 변수(Path Variable)**로, 메서드의 파라메터로 전달 됨.
	 * @PathVariable("id")는 URL 경로에서 id 라는 변수에 해당하는 값을 가져와서 articleID 파라메터에 저장됨.
	 */
	public String viewArticleDetail(@PathVariable("id") Long articleID, Model model) {
		ArticleViewDto article = this.articleService.readArticleViewById(articleID);
		model.addAttribute("article", article);
		return "article_detail";
	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)
	@GetMapping("/create")
	public String viewCreateArticleForm(ArticleSubmitForm articleSubmitForm) {
		/* ArticleSubmitForm 객체가 자동으로 생성이 된다.
		 * 스프링 MVC의 폼 데이터 바인딩 (Form Data Binding) 메커니즘 때문.
		 * 즉, Spring이 자동으로 ArticleSubmitForm 객체를 생성해주는데, 이때 데이터 필드는 모두 null 로 초기화.
		 * 폼 객체가 자동으로 모델에 넣어지기 때문에, 수동으로 Model.addAttribute << ㅇㅈㄹ 안해도 된다.
		 */
		return "article_form";
	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)
	@PostMapping("/create") // /articles/create URL에서 오는 POST 요청을 이 메서드로 매핑시킴.
	public String createArticleForm(@Valid ArticleSubmitForm articleSubmitForm, BindingResult bindingResult) {
		/* @Valid ArticleSubmitForm : 폼에서 제출된 데이터를 ArticleSubmitForm 객체로 받음.
		 * @Valid는 이 객체에 대해 검증을 수행한다는 것을 의미. 즉, ArticleSubmitForm 클래스에서 니가 정의한
		 * 유효성 검증 애너테이션을 적용해서 유효한 데이터인지 판단. (@NotEmpty, @Size, @Pattern, @Email, @AssertTrue...)
		 */
		if (bindingResult.hasErrors()) {
			// 폼 검증에 오류가 있다면 article_form 뷰로 돌아가서, 사용자가 다시 입력할 수 있도록 함.
			return "article_form";
		}
		ArticleViewDto newArticle = this.articleService.createArticle(1L, articleSubmitForm);
		return "redirect:/articles/detail/" + newArticle.getId();
	}

//	@GetMapping("/modify/{id}")
//	public String showUpdateArticleForm(@PathVariable("id") Long articleID, Model model) {
//		/* ArticleSubmitForm은 사용자가 작성할(Create) 또는 수정할(Update) 데이터를 전달하는 DTO(Data Transfer Object)
//		 * ArticleSubmitForm은 자동 모델 바인딩으로 비어있는 객체가 생성되기 때문에 기존 Article의 내용을 불러오기 위해
//		 * articleService로 이미 작성되어있던 엔티티 객체의 데이터를 ArticleMapper의 매핑 메서드로 가공하여 
//		 * null 상태인 폼 객체 ArticleSubmitForm에 집어넣음 
//		 */
//		ArticleSubmitForm modifiedArticleForm = this.articleService.readArticleSubmitFormById(articleID);
//		model.addAttribute("articleSubmitForm", modifiedArticleForm);
//		return "article_form";
//	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)
	@GetMapping("/modify/{id}")
	public String showUpdateArticleForm(@PathVariable("id") Long articleID, ArticleSubmitForm articleSubmitForm) {
		articleSubmitForm = this.articleService.readArticleSubmitFormById(articleID);
		return "article_form";
	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)
	@PatchMapping("/modify/{id}")
	public String updateArticleForm(@Valid ArticleSubmitForm articleSubmitForm, 
			@PathVariable("id") Long articleID, BindingResult bindingResult) {
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
