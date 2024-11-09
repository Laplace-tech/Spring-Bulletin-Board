package com.anna.sbb.apicontroller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.service.UserService;
import com.anna.sbb.viewDto.UserViewDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<UserViewDto>> listAllUsers() {
		return ResponseEntity.ok(this.userService.readAllUsers());
	}

	@GetMapping("/signup")
	public ResponseEntity<UserRegistrationForm> signUp(UserRegistrationForm userRegistrationForm) {
		return ResponseEntity.ok(userRegistrationForm);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserRegistrationForm userRegistrationForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			System.out.println("유효성 검사");
			return ResponseEntity.badRequest().body(userRegistrationForm);
		}

		try {
			UserViewDto createdUser = this.userService.createUser(userRegistrationForm);
			return ResponseEntity.ok(createdUser);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			System.out.println("이미 등록된 사용자임.");
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
		}
		return ResponseEntity.badRequest().body(userRegistrationForm);
	}

}
