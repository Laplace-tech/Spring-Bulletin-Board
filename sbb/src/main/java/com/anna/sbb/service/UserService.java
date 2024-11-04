package com.anna.sbb.service;

import org.springframework.stereotype.Service;

import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	public SiteUser findUserById(Long userID) {
		return this.userRepository.findById(userID)
				.orElseThrow(() -> new IllegalArgumentException("SiteUser Not Found with ID " + userID));
	}
	
}
