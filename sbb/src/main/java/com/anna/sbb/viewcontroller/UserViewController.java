package com.anna.sbb.viewcontroller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.UserViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserViewController {

	private final UserService userService;
	
	
	/*
	 * 회원 가입 페이지로 이동 
	 * 
	 * @param userRegistrationForm 사용자 등록 폼 객체
	 * @return 회원가입 뷰 이름
	 */
	@GetMapping("/signup")
	public String signUp(UserRegistrationForm userRegistrationForm) {
		log.info("[GET: /signup] Accessed signup form");
		return "signup_form";
	}
	
	
	/*
	 * 회원 가입 처리
	 * 
	 * @param userRegistrationForm 사용자 등록 폼 객체
	 * @param bindingResult 유효성 검증 결과
	 * @return 리다이렉트 또는, 회원가입 뷰 이름
	 */
	@PostMapping("/signup")
	public String signUp(@Valid UserRegistrationForm userRegistrationForm,
			BindingResult bindingResult) {
		log.info("[POST: /user/signup] Received registration request for user : [{}]", userRegistrationForm.getUserName());		
		
		log.info("[POST: /user/signup] Validating user's registration form for user : [{}]", userRegistrationForm.getUserName());
		if (bindingResult.hasErrors()) {
			log.warn("[POST: /user/signup] Validation failed for user: [{}]", userRegistrationForm.getUserName());
			return "signup_form";
		}
		log.info("[POST: /user/signup] Validation check result : [{}]", !bindingResult.hasErrors());
		
		
		try {
			log.info("[POST: /user/signup] Creating user with username : [{}]", userRegistrationForm.getUserName());
			UserViewDto newUser = this.userService.createUser(userRegistrationForm);
			log.info("[POST: /user/signup] Successfully created User : [{} - {}]", newUser.getUserName(),
					newUser.getEmail());
		}

		catch (DataIntegrityViolationException e) {
			log.error("[POST: /user/signup] Data Integrity violation for username : [{}] or email : [{}]",
					userRegistrationForm.getUserName(), userRegistrationForm.getEmail(), e.getMessage());
			bindingResult.reject("signupFailed", "이미 등록된 사용자 또는 이메일입니다.");

			return "signup_form";
		}

		catch (Exception e) {
			log.error("[POST: /user/signup] Unexpected error occurred for username : [{}]",
					userRegistrationForm.getUserName(), e.getMessage());
			bindingResult.reject("signupFailed", "회원가입 중 예상치 못한 오류가 발생했습니다.");

			return "signup_form";
		}
		
		return "redirect:/";
	}
	
	/*
	 * 로그인 페이지로 이동
	 * 
	 * @return 로그인 뷰 이름
	 */
	@GetMapping("/login")
	public String login() {
		log.info("[GET: /user/login] Accessed login form");
		return "login_form";
	}
	
}
