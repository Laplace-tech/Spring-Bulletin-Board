package com.anna.sbb.domain;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
// @NoArgsConstructor : 기본 생성자를 자동을 생성시켜주는 애너테이션
@MappedSuperclass
/* @MappedSuperClass : 이 클래스가 JPA 엔티티로 직접 사용되지는 않지만,
 * 다른 엔티티 클래스에서 상속받아 공통 필드와 메서드를 제공하도록 함.
 * 즉, BaseTime 클래스를 상속받은 클래스에 createdDate와 lastModifiedDate가 자동으로 추가됨.
 */
@EntityListeners(AuditingEntityListener.class)
/* @EntityListeners(AuditingEntityListner.class) : JPA의 Auditing 기능을 제공함.
 * 이 리스너는 엔티티가 저장되거나(created) 수정될 때(modified) 자동으로 특정 작업을 수행할 수 있게 해준다
 * 즉, @PrePersist와 @PreUpdate가 작동하도록 설정함. 
 */
@Slf4j // Lombok 을 사용하여 Logger를 자동으로 생성
public abstract class BaseTime {
//  BaseTime 클래스는 JPA 엔티티에서 공통적으로 사용할 시간 정보(생성일자, 수정일자)를 관리함
 
	@Column(nullable = false, updatable = false) // 생성 시만 설정 가능
	private LocalDateTime createdDate;

	@Column(nullable = true)
	private LocalDateTime lastModifiedDate;

	/* @PrePersist : 엔티티가 저장되기 전에 호출되는 메서드에 붙는다.
	 * BaseTime 클래스를 상속하는 @Entity가 처음 JPA를 통해 데이터베이스에 저장될 때,
	 * 자동으로 호출되어 createdDate를 현재 시간으로 설정한다.
	 */
	@PrePersist
	protected void setCreatedDate() {
	    this.createdDate = LocalDateTime.now();
	    log.info("BaseTime @PrePersist setCreatedDate : {}", this.createdDate);
	}

	/* @PreUpdate : 엔티티가 수정되기 전에 호출되는 메서드에 붙는다.
	 * @Entity의 내용이 변경될 때마다 이 메서드가 호출되어 수정 일자를 최신으로 갱신함.
	 */
	@PreUpdate
	protected void updateLastModifiedDate() {
	    this.lastModifiedDate = LocalDateTime.now();
	    log.info("BaseTime @PrePersist updateLastModifiedDate : {}", this.lastModifiedDate);
	}
	
	public LocalDateTime getCreatedDate() {
		log.info("BaseTime @Getter getCreatedDate called. Current createdDate : {}", createdDate);
		return createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		log.info("BaseTime @Getter getLastModifiedDate called. Current createdDate : {}", lastModifiedDate);
		return lastModifiedDate;
	}

}
