package com.anna.sbb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.UserMapper;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.UserViewDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	SiteUser findUserById(Long userID) {
		return this.userRepository.findById(userID)
				.orElseThrow(() -> new IllegalArgumentException("SiteUser Not Found with ID " + userID));
	}

	SiteUser findUserByUserName(String userName) {
		return this.userRepository.findByUserName(userName)
				.orElseThrow(() -> new IllegalArgumentException("SiteUser Not Found with userName " + userName));
	}
	
	public List<UserViewDto> readAllUsers() {
		return this.userRepository.findAll().stream() // @Entity를 가져와서
				.map(userMapper::toViewDto) // 그대로 보여주기 대신, ViewDTO로 변환하여 반환한다.
				.toList();
	}

	public UserViewDto readByUserId(Long userID) {
		return this.userMapper.toViewDto(findUserById(userID));
	}
	
	public UserViewDto readByUserName(String username) {
		return this.userMapper.toViewDto(findUserByUserName(username));
	}

	public UserViewDto createUser(UserRegistrationForm userRegistrationForm) {

		String userName = userRegistrationForm.getUserName();
		String email = userRegistrationForm.getEmail();
		
		if() {
			
		}
		
		SiteUser newUser = this.userMapper.toEntity(userRegistrationForm);
		return this.userMapper.toViewDto(userRepository.save(newUser));
	}
}
