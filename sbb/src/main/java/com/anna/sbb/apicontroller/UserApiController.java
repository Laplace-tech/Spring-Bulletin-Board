package com.anna.sbb.apicontroller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.UserViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;

	private final UserRepository userRepository;
	
	@GetMapping("/entitylist")
	public ResponseEntity<List<SiteUser>> listAllUserEntity() {
		return ResponseEntity.ok(this.userRepository.findAll());
	}
	
	@GetMapping
	public ResponseEntity<List<UserViewDto>> listAllUserViewDto () {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/signup")
	public ResponseEntity<UserRegistrationForm> signUp(UserRegistrationForm userRegistrationForm) {
		return ResponseEntity.ok(userRegistrationForm);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserRegistrationForm userRegistrationForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			System.err.println("폼 데이터 유효성 검사 불합격");
			return ResponseEntity.badRequest().body(userRegistrationForm);
		}

		try {
			UserViewDto createdUser = this.userService.createUser(userRegistrationForm);
			return ResponseEntity.ok(createdUser);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			System.err.println("이미 등록된 사용자 또는 이메일임.");
			bindingResult.reject("signupFailed", "이미 등록된 사용자 또는 이메일입니다.");
		}
		return ResponseEntity.badRequest().body(userRegistrationForm);
	}

	
}
