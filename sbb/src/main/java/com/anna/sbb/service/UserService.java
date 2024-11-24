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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService{

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	// *** Helper Methods *** 
	
	SiteUser findUserByUserName(String userName) {
		return this.userRepository.findByUserName(userName)
				.orElseThrow(() -> new UsernameNotFoundException("user_name : " + userName + " Not Found Exception"));
	}
	
	// *** CRUD Operation ***
	
	public List<UserViewDto> getAllUsers() {
		
		log.info("[@UserService.getAllUsers] >> Fetching all Users as UserViewDto.");
		
		return this.userRepository.findAll().stream() // @Entity를 가져와서
				.map(userMapper::toViewDto) // 그대로 보여주기 대신, ViewDTO로 변환하여 반환한다.
				.toList();
	}

	public UserViewDto getUserByUserName(String username) {
		
		log.info("[@UserService.getUserByUserName] >> Fetching UserViewDto for Username : [{}]", username);
		
		return this.userMapper.toViewDto(findUserByUserName(username));
	}

	public UserViewDto createUser(UserRegistrationForm userRegistrationForm) {
		
		String userName = userRegistrationForm.getUserName();
		String email = userRegistrationForm.getEmail();
		
		log.info("[@UserService.createUser] >> Creating new User with Username : [{}] - Email : [{}]", userName, email);
		
		
		// 중복 확인
		boolean isUserNameTaken = userRepository.findByUserName(userName).isPresent();
		boolean isEmailTaken = userRepository.findByEmail(email).isPresent();

		log.info("[@UserService.createUser] >> Is Username taken? [{}], Is Email taken? [{}]", isUserNameTaken, isEmailTaken);

		if (isUserNameTaken || isEmailTaken) {
		    throw new DataIntegrityViolationException("이미 존재하는 사용자 이름 또는 이메일입니다.");
		}
		log.info("[@UserService.createUser] >> Data Integrity Violation Check passed.");
		
		
		
		log.info("[@UserService.createUser] >> Creating new User with Username : [{}] ", userName);
		
		SiteUser newUser = this.userMapper.toEntity(userRegistrationForm);
		newUser = this.userRepository.save(newUser);
		
		log.info("[@UserService.createUser] >> Successfully Created new User with Username : [{}]", userName);
		
		return this.userMapper.toViewDto(newUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("[@UserService.loadUserByUsername] >> Attempting to load user by Username : [{}]", username);
		
		// 1. 사용자 정보 조회
		SiteUser user;
		try {
			user = this.findUserByUserName(username);
			log.info("[@UserService.loadUserByUsername] >> User found! (Username : [{}] - Email : [{}])", user.getUserName(), user.getEmail());
		} catch (UsernameNotFoundException e) {
			log.warn("[@UserService.loadUserByUsername] >> User not found for Username : [{}]", username);
			throw e;
		}
		
		// 2. 사용자 권한 설정
		String role = "admin".equals(username) ? UserRole.ADMIN.getValue() : UserRole.USER.getValue();
		log.info("[@UserService.loadUserByUsername] >> Assigned role : [{}]", role);
		
		// 3. 권한을 GrantedAuthority 로 변환
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		log.debug("[@UserService.loadUserByUsername] >> Granted authorities: [{}]", authorities);
		/* 
		 *  UserDetails 객체 반환 : User 는 security.core.userdetails.User 클래스의 
		 *  인스턴스로 UserDetails 인터페이스를 구현한 객체이다.
		 *  
		 *  public User(String username, String password, Collection<? extends GrantedAuthority> authorities)
		 *  
		 *  User 객체에는 사용자 이름, 암호화된 비밀번호, 사용자의 권한이 포함된다.
		 */
		
		User userDetails = new User(user.getUserName(), user.getPassword(), authorities);
		log.info("[@UserService.loadUserByUsername] >> Returning UserDetails for Username: [{}]", username);
		return userDetails;
	}
	
}
