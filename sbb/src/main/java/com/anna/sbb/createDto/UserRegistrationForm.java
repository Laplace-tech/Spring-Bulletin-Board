package com.anna.sbb.createDto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@Getter
//@Setter
public class UserRegistrationForm {

	/*
	 * *** Validation Constraints *** 1. @NotNull : 해당 필드가 null이 아니어야 함.
	 * 2. @NotEmpty : 해당 ** 문자열 ** 이 비어있지 않아야 함. "     " 와 같은 문자열도 막힘. 3. @Size :
	 * 문자열의 길이를 제한함. (ex : @Size(min = 2, max = 14) )와 같이 사용 4. @Min : 숫자 필드의 최소값
	 * 설정. 5. @Max : 숫자 필드의 최대값 설정. 6. @Email : 유효한 이메일 형식이어야 함을 확인.
	 */

	// *** Controller ===> Database ***

	@NotEmpty(message = "username is required.")
	@Size(min = 2, max = 14, message = "username must be between 2 and 14 characters.")
	private String userName;

	@NotEmpty(message = "password is required.")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[a-z\\d@$!%*?&]{8,}$", 
	message = "비밀번호는 8자리 이상, 특수문자와 영어 소문자, 숫자를 포함해야 합니다.")
	@Size(min = 8, max = 16, message = "비밀번호는 최소 8자리, 최대 16자리 입니다..")
	private String password;

	@NotEmpty(message = "password confirmation is required.")
	private String checkPassword;

	@NotEmpty(message = "email is required.")
	@Email(message = "email should be valid.")
	private String email;

	@AssertTrue(message = "Passwords must match.")
	public boolean isPasswordMatch() {
		return password != null && checkPassword != null 
				&& password.trim().equals(checkPassword.trim());
	}

	public String getUserName() {
		System.err.println("getter userName : " + userName);
		return userName;
	}

	public void setUserName(String userName) {
		System.err.println("setter userName : " + userName);
		this.userName = userName;
	}

	public String getPassword() {
		System.err.println("getter password : " + password);
		return password;
	}

	public void setPassword(String password) {
		System.err.println("setter password : " + password);
		this.password = password;
	}

	public String getCheckPassword() {
		System.err.println("getter checkPassword : " + checkPassword);
		return checkPassword;
	}

	public void setCheckPassword(String checkPassword) {
		System.err.println("setter checkPassword : " + checkPassword);
		this.checkPassword = checkPassword;
	}

	public String getEmail() {
		System.err.println("getter email : " + email);
		return email;
	}

	public void setEmail(String email) {
		System.err.println("setter email : " + email);	
		this.email = email;
	}

}
