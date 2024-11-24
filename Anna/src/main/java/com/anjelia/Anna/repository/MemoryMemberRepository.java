package com.anjelia.Anna.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.anjelia.Anna.domain.Member;

@Repository
public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> repository = new HashMap<>();
	private static long sequence = 0L;
	@Override
	public Member save(Member member) {
		member.setId(sequence++);
		return repository.put(member.getId(), member);
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(repository.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		return repository.values().stream()
				.filter(member -> member.getName().equals(name))
				.findAny();
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(repository.values());
	}
	
	public void clearRepository() {
		repository.clear();
	}

}
