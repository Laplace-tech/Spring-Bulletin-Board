package com.anna.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
// @Configuration은 이 파일이 스프링의 환경 설정 파일임을 의미함 
@EnableWebSecurity
/* @EnableWebSecurity는 모든 리퀘스트 URL이 스프링 시큐리티의 제어를 받도록 만듬 
 * 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 
 * 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있음
 */
@EnableMethodSecurity(prePostEnabled = true)
/* @EnableMethodSecurity(prePostEnabled = true) 메서드 수준의 보안을 활성화함
 * 메서드에 대한 권한 검사를 위해 @PreAuthorize, @PostAuthorize 등의 어노테이션을 사용할 수 있음.
 */
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		/* authorizeHttpRequests : HTTP 요청에 대한 보안 권한을 설정함.
		 * requestMatchers : 지정된 패턴에 대해 요청을 필터링 함.
		 * AntPathRequestMatcher("/**") : 모든 요청(/**)에 대해 접근을 허용함
		 * 		즉, 별도의 인증 없이 누구나 모든 페이지에 접근할 수 있음.
		 * permitAll() : 위에서 설정한 요청에 대해 인증 없이 접근을 허용
		 * 
		 * 이거 안쓰면 개나소나 로그인 하라고 뜸.
		 */
		.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
		/* csrf(...) : CSRF(교차 사이트 요청 위조) 보호를 설정
		 * ignoringRequestMatchers : 지정된 요청 패턴에 대해 csrf보호를 무시하도록 설정.
		 * AntPathRequestMatcher("/h2-console/")** : H2 데이터베이스 콘솔에 대한 요청은 csrf 보호를 무시하도록  설정.
		 * 
		 * 이거 안쓰면 403 Forbidden 오류 뜸. 
		 * 403(Forbidden) : 서버에 클라이언트 요청이 들어왔으나 서버가 클라이언트의 접근을 거부했을 때 반환
		 *		즉, 당신이 데이터베이스에 대한 접근 권한이 없다는 소리임 ㅇㅇ.
		 */
		.headers(headers -> headers.addHeaderWriter(
			    new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

		http.csrf().disable();
		
		return http.build();
	}
	
}
