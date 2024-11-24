package com.anjelia.Anna.service;

import java.util.List;

import com.anjelia.Anna.domain.Member;
import com.anjelia.Anna.repository.MemoryMemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberService {

	private final MemoryMemberRepository memberRepository;

	// *** Helper method ***

	private void validateDuplicateMember(Member member) {
		this.memberRepository.findByName(member.getName()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}

	// *** CRUD Operation ***

	public Long createMember(Member newMember) {
		validateDuplicateMember(newMember);
		this.memberRepository.save(newMember);
		return newMember.getId();
	}

	public List<Member> getAllMembers() {
		return this.memberRepository.findAll();
	}

	public Member getMemberById(Long memberID) {
		return this.memberRepository.findById(memberID)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID : " + memberID));
	}

	public Member getMemberByName(String memberName) {
		return this.memberRepository.findByName(memberName)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 NAME : " + memberName));
	}

}
