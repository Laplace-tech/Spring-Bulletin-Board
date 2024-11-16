package com.anna.sbb.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.domain.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{

	private final UserService userService;
	
	/* *** 사용자 로그인 인증 흐름 *** 
	 * 
	 * 1. fh
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 1. 사용자 정보 조회 : findUserByUserName 을 호출하여 
		// username 에 해당하는 사용자 정보를 @Repository 에서 조회해 반환한다. 
		SiteUser user = this.userService.findUserByUserName(username);
		
		// 2. 사용자 권한 설정
		String role = "admin".equals(username) ? UserRole.ADMIN.getValue() : UserRole.USER.getValue();
		
		// 3. 권한을 GrantedAuthority 로 변환
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		
		System.err.println(user.getUserName()); // Anna
		System.err.println(user.getPassword()); // $2a$10$wIB3Tr0ZvrKLQoI3O0qUWOq7222g68t5feA1ZOOyCcUWGk48fZXRG
		System.err.println(authorities); // [ROLE_USER]
		
		/*  UserDetails 객체 반환 : User 는 security.core.userdetails.User 클래스의 
		 *  인스턴스로 UserDetails 인터페이스를 구현한 객체이다.
		 *  
		 *  public User(String username, String password, Collection<? extends GrantedAuthority> authorities)
		 *  
		 *  User 객체에는 사용자 이름, 암호화된 비밀번호, 사용자의 권한이 포함된다.
		 */
		return new User(user.getUserName(), user.getPassword(), authorities);
	}
}
