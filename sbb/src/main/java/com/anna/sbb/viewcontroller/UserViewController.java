package com.anna.sbb.viewcontroller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserViewController {

	private final UserService userService;
	
//	.formLogin(formLogin -> formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
	@GetMapping("/signup")
	public String signUp(UserRegistrationForm userRegistrationForm) {
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signUp(@Valid UserRegistrationForm userRegistrationForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		try {
			System.out.println("create User sentence");
			this.userService.createUser(userRegistrationForm);
		} catch (DataIntegrityViolationException e) {
			bindingResult.reject("signupFailed", "이미 등록된 사용자 또는 이메일입니다.");
			return "signup_form";
		} 
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}
	
}
