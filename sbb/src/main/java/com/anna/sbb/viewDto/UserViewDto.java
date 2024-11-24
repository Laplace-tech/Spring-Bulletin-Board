package com.anna.sbb.viewDto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Builder
public class UserViewDto {

	private String userName;
	private String email;

	public UserViewDto(String userName, String email) {
		this.userName = userName;
		this.email = email;

//        log.info("[UserViewDto.@Builder] >> userName=[{}], email=[{}]", userName, email);
	}
}
