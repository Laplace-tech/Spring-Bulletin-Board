package com.anna.sbb.createDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
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
	@Size(min = 3, max = 10, message = "username must be between 3 and 10 characters.")
	private String userName;
	
	@NotEmpty(message = "password is required.")
	@Size(min = 6, max = 20, message = "password must be between 6 and 20 length.")
	private String password;
	
	@NotEmpty(message = "email is required.")
    @Email(message = "email should be valid.")
	private String email;
	
}
