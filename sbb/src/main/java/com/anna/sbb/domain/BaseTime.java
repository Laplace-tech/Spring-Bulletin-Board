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

    @CreatedDate
    @Column(nullable = false, updatable = false) // 생성 시만 설정 가능
    private LocalDateTime createdDate;

    @Column(nullable = true) 
    private LocalDateTime lastModifiedDate;

    // 엔티티가 처음 저장될 때 호출
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    // 엔티티가 수정될 때 호출
    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
