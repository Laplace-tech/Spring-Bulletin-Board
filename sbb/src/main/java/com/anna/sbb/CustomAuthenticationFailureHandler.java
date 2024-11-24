package com.anna.sbb;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		if (exception instanceof UsernameNotFoundException) {
			log.warn(
					"[@CustomAuthenticationFailure.onAuthenticationFailureHandler] >> Authentication Failed : User not found. Error : {}",
					exception.getMessage());
		} else if (exception instanceof BadCredentialsException) {
			log.warn(
					"[@CustomAuthenticationFailure.onAuthenticationFailureHandler] >> Authentication failed: Bad credentials provided.");
		}
		response.sendRedirect("/user/login?error");
	}

}
