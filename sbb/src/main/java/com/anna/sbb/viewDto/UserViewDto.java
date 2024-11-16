package com.anna.sbb.viewDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserViewDto {

	private String userName;
	private String email;
	
	@Builder
	public UserViewDto(String userName, String email) {
		this.userName = userName;
		this.email = email;
	}
 	
}