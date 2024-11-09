package com.anna.sbb.createDto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationForm {

	/*  *** Validation Constraints *** 
	 *  1. @NotNull : 해당 필드가 null이 아니어야  함.
	 *  2. @NotEmpty : 해당 ** 문자열 ** 이 비어있지 않아야 함. "     " 와 같은 문자열도 막힘.
	 *  3. @Size : 문자열의 길이를 제한함. (ex : @Size(min = 2, max = 14) )와 같이 사용
	 *  4. @Min : 숫자 필드의 최소값 설정.
	 *  5. @Max : 숫자 필드의 최대값 설정.
	 *  6. @Email : 유효한 이메일 형식이어야 함을 확인.
	 */
	
	// *** Controller ===> Database *** 
	
	@NotEmpty(message = "username is required.")
	@Size(min = 2, max = 14, message = "username must be between 2 and 14 characters.")
	private String userName;
	
	@NotEmpty(message = "password is required.")
	@Size(min = 6, max = 16, message = "password must be between 6 and 20 length.")
	private String password;
	
	@NotEmpty(message = "password confirmation is required.")
	private String checkPassword;
	
	@NotEmpty(message = "email is required.")
    @Email(message = "email should be valid.")
	private String email;
	
	@AssertTrue(message = "Passwords must match.")
	public boolean isPasswordMatch() {
		return password != null 
				&& checkPassword != null 
				&& password.trim().equals(checkPassword.trim());
	}
	
}
