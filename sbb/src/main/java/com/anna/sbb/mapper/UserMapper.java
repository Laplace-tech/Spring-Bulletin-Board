package com.anna.sbb.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.UserViewDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {

	private final PasswordEncoder passwordEncoder;
	
	// *** Controller ===> Database ***
	// *** Convert "UserRegistration" to "SiteUser"
	public SiteUser toEntity(UserRegistrationForm userRegistrationForm) {
		
		System.err.println("User Mapper - toEntity");
		
		return SiteUser.builder()
				.userName(userRegistrationForm.getUserName())
				.password(passwordEncoder.encode(userRegistrationForm.getPassword()))
				.email(userRegistrationForm.getEmail())
				.build();
	}
	
	// *** Controller <=== Database ***
	// *** Convert "SiteUser" to "UserViewDto"
	public UserViewDto toViewDto(SiteUser user) {
		
		System.err.println("User Mapper - toViewDto");
		
		return UserViewDto.builder()
				.userName(user.getUserName())
				.email(user.getEmail())
				.build();
	}
	
	// UserViewDto <------------------\
	//								   ---- SiteUser(Entity)
	//								------->
	// UserRegistrationForm -------/
}
