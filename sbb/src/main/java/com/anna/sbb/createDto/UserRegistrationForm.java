package com.anna.sbb.createDto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationForm {

	/*
	 * *** Validation Constraints *** 1. @NotNull : 해당 필드가 null이 아니어야 함.
	 * 2. @NotEmpty : 해당 ** 문자열 ** 이 비어있지 않아야 함. "     " 와 같은 문자열도 막힘. 3. @Size :
	 * 문자열의 길이를 제한함. (ex : @Size(min = 2, max = 14) )와 같이 사용 4. @Min : 숫자 필드의 최소값 설정
	 * 5. @Max : 숫자 필드의 최대값 설정. 6. @Email : 유효한 이메일 형식이어야 함을 확인.
	 */

	@NotEmpty(message = "username is required.")
	@Size(min = 2, max = 14, message = "username must be between 2 and 14 characters.")
	private String userName;

	@NotEmpty(message = "password is required.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{8,}$", message = "비밀번호는 8자리 이상, 특수문자와 영어 소문자, 숫자를 포함해야 합니다.")
	@Size(min = 8, max = 16, message = "비밀번호는 최소 8자리, 최대 16자리 입니다..")
	private String password;

	@NotEmpty(message = "password confirmation is required.")
	private String checkPassword;

	@NotEmpty(message = "email is required.")
	@Email(message = "email should be valid.")
	private String email;

	@AssertTrue(message = "Passwords must match.")
	public boolean isPasswordMatch() {
		
		boolean result = password != null && checkPassword != null && password.trim().equals(checkPassword.trim());
		
		log.info("[UserRegistration.isPasswordMatch] >> Validating password match: password={}, checkPassword={}, result={}", password, checkPassword, result);
		
		return result;
	}

//	직접 작성한 Getter & Setter
//	public void setUserName(String userName) {
//		log.info("UserRegistrationForm @Setter setUserName. Previous userName={}, New userName={}", this.userName, userName);
//		this.userName = userName;
//	}
//
//	public void setPassword(String password) {
//		log.info("UserRegistrationForm @Setter setPassword. Previous password={}, New password={}", this.password, password);
//		this.password = password;
//	}
//
//	public void setCheckPassword(String checkPassword) {
//		log.info("UserRegistrationForm @Setter setCheckPassword. Previous checkPassword={}, New checkPassword={}", this.checkPassword, checkPassword);
//		this.checkPassword = checkPassword;
//	}
//
//	public void setEmail(String email) {
//		log.info("UserRegistrationForm @Setter setEmail. Previous email={}, New email={}", this.email, email);
//		this.email = email;
//	}
//
//	public String getUserName() {
//		log.info("UserRegistrationForm @Getter getUserName called. Current userName={}", this.userName);
//		return userName;
//	}
//
//	public String getPassword() {
//		log.info("UserRegistrationForm @Getter getPassword called. Current password={}", this.password);
//		return password;
//	}
//
//	public String getCheckPassword() {
//		log.info("UserRegistrationForm @Getter checkPassword called. Current checkPassword={}", this.checkPassword);
//		return checkPassword;
//	}
//
//	public String getEmail() {
//		log.info("UserRegistrationForm @Getter getEmail called. Current email={}", this.email);
//		return email;
//	}

}
