package com.anna.sbb.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass 
// *** @MappedSuperclass *** 
// 이를 상속받는 모든 @Entity들은 클래스에 createdDate와 lastModifiedDate를 포함한다.
@EntityListeners(AuditingEntityListener.class)
// *** @EntityListners ***
// @CreatedDate, @LastModifiedDate와 함께 사용되어
// @Entity의 생성 or 수정 이벤트를 감지하고 자동으로 날짜 정보를 설정
public abstract class BaseTime {

	@CreatedDate
	// *** @CreatedDate ***
	// @Entity가 처음 설정될 때의 날짜를 자동으로 기록
	@Column(updatable = false, nullable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	// *** @LastModifiedDate *** 
	// @Entity가 수정될 때마다 날짜를 자동으로 기록
	@Column(nullable = false)
	private LocalDateTime lastModifiedDate;

}
