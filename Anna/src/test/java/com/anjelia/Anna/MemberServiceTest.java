package com.anjelia.Anna;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.anjelia.Anna.domain.Member;
import com.anjelia.Anna.repository.MemoryMemberRepository;
import com.anjelia.Anna.service.MemberService;

@SpringBootTest
public class MemberServiceTest {

	private MemoryMemberRepository memberRepository;
	private MemberService memberService;

	@BeforeEach
	public void beforeEach() {
		this.memberRepository = new MemoryMemberRepository();
		this.memberService = new MemberService(memberRepository);
	}

	@AfterEach
	public void afterEach() {
		this.memberRepository.clearRepository();
	}

	@Test
	public void duplicateException() {

		// given
		Member m1 = new Member();
		m1.setName("Anna");
		Member m2 = new Member();
		m2.setName("Anna");

		// When
		this.memberService.createMember(m1);
		IllegalStateException e = assertThrows(IllegalStateException.class, 
				() -> memberService.createMember(m2));
																												// 발생해야
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}

}
