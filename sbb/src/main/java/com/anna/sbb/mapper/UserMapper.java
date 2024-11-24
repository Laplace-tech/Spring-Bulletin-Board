package com.anna.sbb.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    	
//    	log.info("[toEntity] >> Mapping UserRegistrationForm to SiteUser.  Username : {}, Email : {}",
//				userRegistrationForm.getUserName(), userRegistrationForm.getEmail());
    	
        SiteUser siteUser = SiteUser.builder()
                .userName(userRegistrationForm.getUserName())
                .password(passwordEncoder.encode(userRegistrationForm.getPassword()))
                .email(userRegistrationForm.getEmail())
                .build();

        return siteUser;
    }

    // *** Controller <=== Database ***
    // *** Convert "SiteUser" to "UserViewDto" ***
    public UserViewDto toViewDto(SiteUser siteUser) {

//		log.info("[toViewDto] >> Mapping SiteUser to UserViewDto. Username : {}, Email : {}",
//				siteUser.getUserName(), siteUser.getEmail());

		UserViewDto userViewDto = UserViewDto.builder()
				.userName(siteUser.getUserName())
				.email(siteUser.getEmail())
				.build();

		return userViewDto;
    }

    // UserViewDto <------------------\
    //								   ---- SiteUser(Entity)
    //								------->
    // UserRegistrationForm -------/
}
