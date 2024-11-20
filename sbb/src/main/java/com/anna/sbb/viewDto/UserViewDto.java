package com.anna.sbb.viewDto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class UserViewDto {

    private String userName;
    private String email;

    @Builder
    public UserViewDto(String userName, String email) {
        this.userName = userName;
        this.email = email;

        // 로깅 추가
        log.info("UserViewDto @Builder created: userName={}, email={}", userName, email);
    }
}
