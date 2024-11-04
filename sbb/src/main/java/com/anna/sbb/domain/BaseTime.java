package com.anna.sbb.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

	@Column(nullable = false, updatable = false) // 생성 시만 설정 가능
	private LocalDateTime createdDate;

	@Column(nullable = true)
	private LocalDateTime lastModifiedDate;

	@PrePersist
	protected void setCreatedDate() {
	    this.createdDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void updateLastModifiedDate() {
	    this.lastModifiedDate = LocalDateTime.now();
	}

}
