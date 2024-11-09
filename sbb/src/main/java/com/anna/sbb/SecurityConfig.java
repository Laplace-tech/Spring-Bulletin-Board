package com.anna.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
@EnableWebSecurity
/*
 * @EnableWebSecurity : Spring Security를 활성화하는 애너테이션으로, 이 애너테이션이 붙은 클래스에서 웹 보안
 * 설정을 지정할 수 있음 (ex : 사용자 인증과 권한 부여)
 */
@EnableMethodSecurity(prePostEnabled = true)
/*
 * 메서드 수준에서의 보안 기능을 활성화함.
 * 
 * @PreAuthorize나 @PostAuthorize와 같은 애너테이션을 사용하여 메서드 접근을 제어할 수 있도록 함. 특정 권한이 있는
 * 사용자만 특정 메서드를 호출할 수 있게 제한할 때, 사용.
 */
public class SecurityConfig {

    /* CSRF(Cross-Site Request Forgery) 보호 기능은 CSRF 공격을 방지하기 위해 페이지를 요청할 때마다 
     * 서버가 임의로 고유한 CSRF 토큰을 생성하고 검증하는 방식임. CSRF는 사용자가 신뢰하는 웹 사이트의 요청을 위장하여 
     * 공격자가 사용자의 세션을 악용하는 것을 말함. Spring Security는 이를 방지하기 위해 사용자가 보내는 요청에 
     * CSRF 토큰을 요구하며, 이 토큰이 없거나 올바르지 않으면 요청을 차단함.
     *
     * 왜 403 Forbidden 오류가 발생하나? 
     * 	ㄴ> H2 콘솔은 CSRF 토큰을 생성하거나 검증하는 기능이 없음. 
     * 		그래서 SecurityFilterChain에서 의도적으로 H2 콘솔에 대해 CSRF 보호를 비활성화 시켜야 함. ㅇㅋ?
     */
    
    /* @Bean 이란?
     * 	ㄴ> @Bean은 스프링에 의해 생성 또는 관리되는 객체를 의미한다. @Controller, @Service, @Repository등 
     * 		모두 @Bean에 해당된다. 또한, 직접 @Bean 애너테이션을 통해 자바 코드 내에서 별도로 @Bean을 정의하고 등록할 수도 있다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize 
				-> authorize.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
		/* authorizeHttpRequests : 이 메서드는 요청에 대한 접근 권한(Access Authentification)을 정의함.
		 * requestMatchers : 특정 URL 패턴과 일치하는 요청을 처리함. 
		 * "/**"는 모든 경로를 의미하므로, 애플리케이션의 모든 리퀘스트에 대응함.
		 * permitAll() : 해당 경로("/**")에 대해 인증 없이 접근을 허용함.
		 */
				.csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
				/* Spring Security에서 CSRF 보호를 설정하면서, 특정 경로("/h2-console/**)에 대해서는 CSRF 검사를 무시하도록 설정.
				 * .csrf(...) : CSRF 보호를 적용하여 외부 사이트가 악의적으로 요청을 보내는 것을 방지.
				 * ingnoringRequestMatchers(..) 특정 요청 경로에 대해 CSRF 보호를 무시하게 설정.
				 */
				.headers(headers -> headers.addHeaderWriter(
						new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
				.formLogin(formLogin -> formLogin.loginPage("/user/login").defaultSuccessUrl("/"))
				/* formLogin : 폼 기반 인증을 설정함. 여기서는 "/user/login" 경로로 커스터마이징하여 로그인 페이지를 지정함.
				 * defaultSuccessUrl("/") : 로그인 후 기본적으로 리다이렉트될 URL을 지정함.
				 * 만약, 사용자가 특정 페이지에 접근하려다가 로그인 페이지로 리다이렉트 되었다면, 로그인 후 원래 요청했던 페이지로 이동하게됨.
				 */
				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
						.logoutSuccessUrl("/").invalidateHttpSession(true));
				/* logout : 로그아웃 URL을 "/user/logout"으로 설정함. 이 경로로 요청이 오면 로그아웃이 처리됨.
				 * logoutSuccessUrl("/") : 로그아웃 성공 후, "/"로 이동함. 
				 * invalidateHttpSession(true) : 로그아웃 시 세션을 무효화하여 사용자 정보를 초기화함.
				 */
		 http.csrf().disable();
		 /* 이게 없으면 403 Forbidden 오류가 발생하더라...
		  * Spring Security에서는 CSRF 보호 기능을 활성화한 경우에, POST, PUT, DELETE 리퀘스트가 
		  * CSRF 토큰을 포함하지 않으면 403 Forbidden 오류가 발생할 수 있음.
		  * 만약 클라이언트가 CSRF 토큰을 제공하지 않으면 서버에서 이를 차단함. 
		  */

		return http.build();
	}

    /* @Bean은 IoC(Inversion of Control) 컨테이너에서 관리되는 객체다. 
     * Spring은 의존성 주입(Dependency Injection)을 통해 필요한 객체들을 자동으로 주입함.
     * @Bean으로 등록된 객체들은 스프링 컨텍스트가 시작될 때, 컨테이너에 빈으로 등록되고 스프링 애플리케이션에서 이를 
     * 사용할 수 있음.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
		/* 반환하는 BCryptPasswordEncoder 객체를 생성하여 Spring의 빈 컨테이너에 등록한다.
		 * 이를 애플리케이션 어디에서나 주입받을 수 있게된다. 즉, 클래스마다 각각 새로운 객체를 생성할 필요 없이
		 * @Bean 객체를 참조할 수 있는 변수를 선언하기만 하면 그것을 사용할 수 있다는 뜻.
		 */
		return new BCryptPasswordEncoder();
	}

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
    
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//			throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}

}
