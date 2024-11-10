package com.anna.sbb.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.domain.UserRole;
import com.anna.sbb.mapper.UserMapper;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.viewDto.UserViewDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService{

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
		
		if(userRepository.findByUserName(userName).isPresent() || userRepository.findByEmail(email).isPresent()) {
			throw new DataIntegrityViolationException("이미 존재하는 사용자 이름 또는 이메일입니다.");
		}
		
		SiteUser newUser = this.userMapper.toEntity(userRegistrationForm);
		return this.userMapper.toViewDto(userRepository.save(newUser));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SiteUser user = this.userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

		// 권한 설정
		String role = "admin".equals(username) ? UserRole.ADMIN.getValue() : UserRole.USER.getValue();
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		
		return new User(user.getUserName(), user.getPassword(), authorities);
	}
}