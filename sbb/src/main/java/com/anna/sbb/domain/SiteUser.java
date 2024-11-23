package com.anna.sbb.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@Getter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, updatable = false)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Builder
	public SiteUser(String userName, String password, String email) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		
		// 로깅
		log.info("[SiteUser] @Builder SiteUser (userName:{}, password:{}, email:{}", userName, password, email);
	}

//	public Long getId() {
//		log.info("SiteUser @Getter getId called. Current id : {}", id);
//		return id;
//	}
//
//	public String getUserName() {
//		log.info("SiteUser @Getter getUserName called. Current userName : {}", userName);
//		return userName;
//	}
//
//	public String getPassword() {
//		log.info("SiteUser @Getter getPassword called. Current password : {}", password);
//		return password;
//	}
//
//	public String getEmail() {
//		log.info("SiteUser @Getter getEmail called. Current email : {}", email);
//		return email;
//	}
	
}
