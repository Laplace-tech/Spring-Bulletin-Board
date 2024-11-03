package com.anna.sbb.domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
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
	}
	
}
