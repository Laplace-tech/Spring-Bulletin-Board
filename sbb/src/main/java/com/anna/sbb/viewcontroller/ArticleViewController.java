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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.viewDto.ArticleViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/articles")
/*
 * @RequestMapping : HTTP 요청과 이를 처리할 메서드를 매핑하는 데 사용됨.
 * 
 * @RequestMapping을 클래스 레벨에 적용하면, 해당 클래스의 모든 메서드에서 /articles로 시작하는 경로를 처리함.
 */
@RequiredArgsConstructor
/*
 * @RequiredArgsConstructor : 클래스 내에서 final 필드나, @NonNull이 붙은 필드에 대해 생성자가 자동으로
 * 생성됨. 필드에 대한 의존성 주입을 자동으로 처리할 수 있음. @Autowired를 사용하지 않아도 됨.
 */
@Controller
/*
 * @Controller는 Spring MVC(Model-View-Controller)에서 컨트롤러 클래스를 선언하는 데 사용되는 애너테이션.
 * 
 * @Controller는 웹 리퀘스트를 처리하고 모델에 객체 데이터를 입혀서(추가해서) 뷰를 반환하는 역할을 함. 주로 웹 애플리케이션에서
 * HTTP 요청을 처리하는 클래스에 사용되며, 리턴타입이 뷰이름인 경우에 사용함.
 * 
 * @Controller가 붙은 클래스는 Spring 의 DispatcherServlet이 클라이언트로 보낸 요청을 처리할 때, 해당 클래스를
 * 컨트롤러로 인식하게 됨. 이때 컴포넌트 스캐너가 작동함.
 */
public class ArticleViewController {

	// 의존성 주입 (Dependency Injection)
	private final ArticleService articleService;

	/*
	 * @RequiredArgsConstructor 는 아래와 같은 생성자를 자동으로 생성해주는 기능을 갖는다. 
	 * 스프링은 자동으로 ArticleService 빈을 찾아서 전달한다.
	 * 
	 * public ArticleViewController(ArticleService articleService) {
	 * 		this.articleService = articleService; 
	 * }
	 */

	@GetMapping // @RequestMapping(method = RequestMethod.GET)와 동일
	public String viewArticlesByPage(@RequestParam(value = "page", defaultValue = "0") int pageNum, Model model) {
		
		log.info("[viewArticlesByPage] >> @GetMapping request received");
		
		/*
	     * @RequestParam : HTTP 요청 URL에서 파라메터 값을 메서드 인자로 바인딩하는 애너테이션. 
	     * value = "page" : HTTP 요청의 파라메터 이름이 **page**인 값을 가져오겠다는 의미임.
	     * defaultValue = "0" : page 파라메터가 포함되어있지 않으면 기본값으로 0을 사용함.
	     * 즉, /articles 라고 리퀘스트를 보내면 /articles?page=0 으로 자동 설정.
	     * 
	     * **Model** 은 스프링 MVC에서 뷰(View)로 전달할 객체 데이터를 담는 객체임. 
	     * 컨트롤러 메서드에서 Model 객체의 Attribute 메서드를 통해 넘길 데이터를 준비하고 이를 뷰로 전달
	     */

		// 로깅 : 요청된 페이지 번호를 출력하는  info 로그 추가
		log.info("[viewArticlesByPage] >> Fetching articles for page number : {}", pageNum);
		
		Page<ArticleViewDto> articlePage = this.articleService.readArticleViewPage(pageNum);
		model.addAttribute("articlePage", articlePage);
		
		// 로깅 : 페이지 조회 후 결과 출력
		log.info("[viewArticlesByPage] >> Fetched {} articles for page {}", articlePage.getContent().size(), pageNum);
		
		return "article_list";
	}

	/*
	 * /detail/{id}는 URL 경로에서 id 값을 동적으로 받겠다는 의미. 
	 * {id} 부분은 **경로 변수(Path Variable)** 로서, 메서드의 파라메터로 전달 됨.
	 * 
	 * @PathVariable("id")는 URL 경로에서 id 라는 변수에 해당하는 값을 가져와서 articleID 파라미터에 저장됨.
	 * 
	 * @ModelAttribute (at method @GetMapping) : ** 모델에 객체 추가 **
	 * 
	 * @ModelAttribute가 선언된 객체는 메서드가 실행되기 전에 스프링이 자동으로 객체를 생성하고 이를 모델에 추가시킨다. 
	 * 하지만 이 객체가 @Bean으로 분류되지는 않는다. @Bean 과 아무런 연관이 없음.
	 */
	@GetMapping("/detail/{id}")
	public String viewArticleDetail(@PathVariable("id") Long articleID,
			@ModelAttribute CommentSubmitForm commentSubmitForm, Model model) {
		
		log.info("[viewArticleDetail] >> @GetMapping request received");

		// 로깅 : 어떤 글의 세부 정보를 띄울 것인지 출력
		log.info("[viewArticleDetail] >> Fetching details for article with ID : {}", articleID);

		// 글 정보 조회
		ArticleViewDto article = this.articleService.readArticleViewById(articleID);
		
		// 모델에 추가 : model.addAttribute()
		model.addAttribute("article", article);
		
		// 로깅 : 
		log.info("[viewArticleDetail] >> Fetched article details for ID : {} - Title : {}", article.getId(), article.getTitle());
		
		return "article_detail";
	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)

	/*
	 * @PreAuthorize("isAuthenticated()") 는 메소드 보안기능을 설정함.
	 * @PreAuthorize가 붙은 메서드는 실행되기 전에 인증된 사용자만 해당 메서드를 실행할 수 있도록 제한함.
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/create")
	public String viewCreateArticleForm(@ModelAttribute ArticleSubmitForm articleSubmitForm, Principal principal) {
		
		log.info("[viewCreateArticleForm] >> @GetMapping request received by user : {}", principal.getName());
		
		/*
		 * @ModelAttribute : ** 폼 데이터 바인딩 애너테이션 ** 
		 * 		ㄴ> ** 아래와 동일 ** 
		 * 
		 * ArticleSubmitForm articleSubmitForm = new ArticleSubmitForm();
		 * model.addAttribute("articleSubmitForm", articleSubmitForm);
		 * 
		 * 메서드의 파라메터로 사용한다면, 스프링이 자동으로 위 객체를 생성하고 이를 모델에 추가함. 
		 * 따라서, articleSubmitForm 이라는 변명으로 유저 뷰에 전달이 되는데 이때, 
		 * 데이터는 모두 기본값(null)으로 초기화 됨.
		 */
		
		log.info("[viewCreateArticleForm] >> Loading article creation form");
		
		return "article_form";
	}
		
	/*
	 * ArticleSubmitForm은 사용자가 작성할(Create) 또는 수정할(Update) 데이터를 전달하는 객체임 
	 * 오직 데이터만 전달하기 위한 DTO(Data Transfer Object) 이므로 @Service 계층에서 
	 * DTO에 저장된 정보를 통해, 실제 @Entity로 매핑하는 작업을 수행함
	 * 
	 * ArticleSubmitForm은 자동 모델 바인딩으로 필드 값이 모두 null 값으로 비워져 생성되기 때문에
	 * 기존 @Entity의 내용을 불러오기 위해 articleService 통해 이미 작성되어있던 엔티티 객체의 데이터를 불러온다.
	 * 그 데이터를 null 상태인 폼 객체 ArticleSubmitForm에 @Setter 로 집어넣음.
	 * 
	 * **@Controller** <------ **@Service** <------ **@Repository**
	 * ArticleSubmitForm <-- (Mapping & @Builder) <-- (@Entity)
	 * 
	 */

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String viewUpdateArticleForm(@PathVariable("id") Long articleID,
			@ModelAttribute CommentSubmitForm commentSubmitForm, Model model, Principal principal) {
		
		log.info("[viewUpdateArticleForm] >> request received for article ID: {} by user: {}", articleID, principal.getName());

		
		log.info("[viewUpdateArticleForm] >> checking update permission for article ID : {}", articleID);
		
		// 권한 체크
		if (!this.articleService.checkAuthorPermission(articleID, principal)) {
			
			log.warn("[viewUpdateArticleForm] >> Checking update permission failed. "
					+ "User {} attempted  to update article ID : {}", principal.getName(), articleID);
			
			ArticleViewDto article = this.articleService.readArticleViewById(articleID);

			model.addAttribute("article", article);
			model.addAttribute("error", "수정 권한이 없습니다");
			
			log.warn("[viewUpdateArticleForm] >> Returning article detail view (article ID : {}) with error message", articleID);
			return "article_detail";
		}

		// 수정 폼 데이터 가져오기
		ArticleSubmitForm editArticleSubmitForm = this.articleService.readArticleSubmitFormById(articleID);
		model.addAttribute("articleSubmitForm", editArticleSubmitForm);

		/*
		 * 수정 폼 데이터 가져오기 (In another way)
		 * 
		 * 파라미터에 (..., @ModelAttribute ArticleSubmitForm editSubmitForm) 추가하고
		 * 
		 * ArticleSubmitForm articleForm = this.articleService.readArticleSubmitFormById(articleID);
		 * 		ㄴ> @Service로 @Entity 찾아서 SubmitForm 으로 Mapping 한 후,
		 * 
		 * editSubmitForm.setId(articleForm.getId());
		 * editSubmitForm.setTitle(articleForm.getTitle());
		 * editSubmitForm.setContent(articleForm.getContent());
		 * 		ㄴ> ArticleSubmitForm 의 @Setter로 기존 데이터 저장하기
		 */
		
		log.info("[viewUpdateArticleForm] >> Loading update form for article ID : {}", articleID);
		return "article_form"; // 수정 폼 페이지로 이동
	}

//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)

	/*
	 * @PreAuthorize("isAuthenticated()") in Spring Security 인증된 사용자만 해당 메서드를 호출할 수
	 * 있게 함. 즉, 로그인 하지 않았다면 이 메서드를 호출할 수 없음. 인증되지 않은 사용자는 403 Forbidden 에러를 받는다. (접근
	 * 권한 없음)
	 */
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create") // /articles/create URL에서 오는 POST 요청을 이 메서드로 매핑시킴.
	public String createArticleForm(@Valid @ModelAttribute ArticleSubmitForm articleSubmitForm,
			BindingResult bindingResult, Principal principal) {
		
		log.info("[createArticleForm] >> @PostMapping request received by user: {}", principal.getName());
		
		
		log.info("[createArticleForm] >> Creating new article by user : {}", principal.getName());
		
		/*
		 * @Valid @ModelAttribute ArticleSubmitForm : 폼에서 제출된 데이터를 ArticleSubmitForm 객체로 받음.
		 * 
		 * @Valid는 이 객체에 대해 검증을 수행한다는 것을 의미. 즉, ArticleSubmitForm 클래스에서 니가 폼 클래스에 정의한
		 * 유효성 검증 애너테이션 (@NotEmpty, @Size, @Pattern, @Email, @AssertTrue...) 등등 을 적용해서
		 * 유효한 데이터인지 판단하고 이 결과를 BindingResult 객체에 저장한다.
		 *
		 * ----------------------------------------------------------------------------
		 * 
		 * @ModelAttribute : HTTP 리퀘스트 파라미터의 폼 데이터를 지정된 객체에 자동으로 바인딩 해준다. 
		 * 즉, 스프링이 내부적으로 다음과 같이 코드를 실행한다.
		 * 
		 * ** Notice ** 
		 * => Spring 은 폼 데이터 객체를 바인딩할 때, 기본 생성자로 객체를 생성한 뒤, @Setter 를 사용하여 값을 채운다.
		 * 
		 * 1. ArticleSubmitForm submitForm = new ArticleSubmitForm(); ***
		 * => 이걸 할려면 기본 생성자를 자동으로 집어넣어주는 @NoArgsConstructor 가 필요하겠지?
		 * 
		 * 2. submitForm.setTitle(*{title}); 
		 *    submitForm.setContent("*{content}");
		 * => 스프링이 자동으로 값을 저장하려면 @Setter 가 필요하겠지?
		 * 
		 * ----------------------------------------------------------------------------
		 * 
		 * @AuthenticationPrincipal : 현재 인증된 사용자의 정보를 메서드 파라미터로 직접 주입할 때 사용함
		 * 
		 * UserDetails : Spring Security 에서 사용자의 정보를 표현하는 기본 인터페이스로, username, password,
		 * authorities(권한), accountNonExpired(계정만료 여부)등의 메서드를 제공함.
		 * 
		 * Principal : 현재 인증된 사용자의 정보를 담고있음 Spring Security 에서 사용자가 로그인한 후, 현재 사용자의 정보를
		 * 가져오는 방법임. Spring 에서 인증이 완료되면, Principal 객체를 사용할 수 있음.
		 * 
		 */
		
		log.info("[createArticleForm] >> examine form validation for article creation ");
		
		if (bindingResult.hasErrors()) {
			
			log.warn("[createArticleForm] >> Form validation failed for article creation by user : {} "
					+ "Returning article creation form with error message", principal.getName());
			
			// 폼 검증에 오류가 있다면 article_form 뷰로 돌아가서, 사용자가 다시 입력할 수 있도록 함.
			return "article_form";
		}

		ArticleViewDto newArticle = this.articleService.createArticle(principal.getName(), articleSubmitForm);

		log.info("[createArticleForm] >> user : {} Successfully created article with ID : {}",
				principal.getName(), newArticle.getId());

		return String.format("redirect:/articles/detail/%s", newArticle.getId());
	}

	
//	java.lang.IllegalStateException: 
//	  Cannot resolve parameter names for constructor 
//		public ArticleSubmitForm(Long, String, String)
	@PreAuthorize("isAuthenticated()")
	@PatchMapping("/modify/{id}")
	public String updateArticleForm(@Valid ArticleSubmitForm articleSubmitForm, @PathVariable("id") Long articleID,
			BindingResult bindingResult, Model model, Principal principal) {
		
		log.info("[updateArticleForm] >> @PatchMapping request received for article ID: {} by user: {}",
				articleID, principal.getName());
		
		log.info("[updateArticleForm] >> checking update permission for article ID : {}", articleID);
		
		// 권한 체크
		if (!articleService.checkAuthorPermission(articleID, principal)) {
			
			log.warn("[updateArticleForm] >> Checking update permission failed. "
					+ "User {} attempted to update article ID : {}", principal.getName(), articleID);
			
			ArticleViewDto article = this.articleService.readArticleViewById(articleID);
			model.addAttribute("article", article);
			model.addAttribute("error", "수정 권한이 없습니다");
			
			log.warn("[updateArticleForm] >> Returning article detail view (article ID : {}) with error message", articleID);
			return "article_detail";
		}

		
		log.info("[updateArticleForm] >> examine form validation for article update");
		
		// 폼 검증 실패 시, article_form 으로 다시 이동
		if (bindingResult.hasErrors()) {
			
			log.warn("[updateArticleForm] >> Form validation failed for article update by user : {} ", principal.getName());
			
			log.warn("[updateArticleForm] >> Returning article update form with error message");
			return "article_form";
		}

		// 실제 수정 비즈니스 로직 수행
		ArticleViewDto modifiedArticle = this.articleService.updateArticle(articleID, articleSubmitForm);
		
		log.info("[updateArticleForm] >> user : {} Successfully updated article with ID : {}",
				principal.getName(), modifiedArticle.getId());
		
		return "redirect:/articles/detail/" + modifiedArticle.getId();
	}

	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteArticle(@PathVariable("id") Long articleID, Principal principal) {

		log.info("[deleteArticle] >> Delete request received for article ID: {} by user: {}", articleID, principal.getName());
		
		log.info("[deleteArticle] >> checking delete permission for article ID : {}", articleID);

		// 권한 체크
	    if (!articleService.checkAuthorPermission(articleID, principal)) {
	        
	    	log.warn("[deleteArticleForm] >> Checking delete permission failed. "
					+ "User {} attempted to update article ID : {}", principal.getName(), articleID);
	    	
	    	return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body("삭제 권한이 없습니다.");
	    }

	    // 게시글 삭제 수행
	    try {
	        articleService.deleteArticle(articleID);
	        return ResponseEntity.ok("삭제되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("삭제 중 오류가 발생했습니다.");
	    }
	}

	
}
