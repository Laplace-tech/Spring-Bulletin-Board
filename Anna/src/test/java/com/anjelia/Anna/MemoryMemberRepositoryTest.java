package com.anjelia.Anna;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.anjelia.Anna.domain.Member;
import com.anjelia.Anna.repository.MemoryMemberRepository;

@SpringBootTest
public class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach() {
		this.repository.clearRepository();
	}

	@Test
	public void save() {

		// given
		Member member = new Member();
		member.setName("Anna");

		// when
		this.repository.save(member);

		// then
		Member resultMember = this.repository.findByName(member.getName()).get();
		assertThat(resultMember).isEqualTo(member);
	}

	@Test
	public void findByName() {

		// given
		Member m1 = new Member();
		m1.setName("Anna");
		this.repository.save(m1);

		Member m2 = new Member();
		m2.setName("Erma");
		this.repository.save(m2);

		// when
		Member resultMember = this.repository.findByName("Anna").get();

		// then
		assertThat(resultMember).isEqualTo(m1);
	}

	@Test
	public void findAll() {

		// given
		Member m1 = new Member();
		m1.setName("Anna");
		this.repository.save(m1);

		Member m2 = new Member();
		m2.setName("Erma");
		this.repository.save(m2);
		
		Member m3 = new Member();
		m3.setName("Romi");
		this.repository.save(m3);

		// when
		List<Member> memberList = this.repository.findAll();
		
		// then
		assertThat(memberList.size()).isEqualTo(3);
		
	}
}
