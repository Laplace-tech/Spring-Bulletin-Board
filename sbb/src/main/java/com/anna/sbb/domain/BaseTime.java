package com.anna.sbb.domain;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTime {
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;
	
	@Column(nullable = true)
	private LocalDateTime lastModifiedDate;
	
	@PrePersist
	protected void setCreatedDate() {
		log.info("[@BaseTime.setCreatedDate] >> createdDate({} -> {})", this.createdDate, LocalDateTime.now());
		this.createdDate = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void updateLastModifiedDate() {
		log.info("[@BaseTime.updateLastModifiedDate] >> lastModifiedDate({} -> {})", this.lastModifiedDate, LocalDateTime.now());
		this.lastModifiedDate = LocalDateTime.now();
	}
	
}