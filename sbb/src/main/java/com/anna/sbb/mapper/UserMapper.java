package com.anna.sbb.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.*;

import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.UserViewDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    // *** Controller ===> Database ***
    // *** Convert "UserRegistrationForm" to "SiteUser" ***
    public SiteUser toEntity(UserRegistrationForm userRegistrationForm) {
        log.info("Mapping UserRegistrationForm to SiteUser. Input: {}", userRegistrationForm);

        SiteUser siteUser = SiteUser.builder()
                .userName(userRegistrationForm.getUserName())
                .password(passwordEncoder.encode(userRegistrationForm.getPassword()))
                .email(userRegistrationForm.getEmail())
                .build();

        log.info("Mapped SiteUser entity: {}", siteUser);
        return siteUser;
    }

    // *** Controller <=== Database ***
    // *** Convert "SiteUser" to "UserViewDto" ***
    public UserViewDto toViewDto(SiteUser user) {
        log.info("Mapping SiteUser to UserViewDto. Input: {}", user);

        UserViewDto userViewDto = UserViewDto.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .build();

        log.info("Mapped UserViewDto: {}", userViewDto);
        return userViewDto;
    }

    // UserViewDto <------------------\
    //								   ---- SiteUser(Entity)
    //								------->
    // UserRegistrationForm -------/
}
