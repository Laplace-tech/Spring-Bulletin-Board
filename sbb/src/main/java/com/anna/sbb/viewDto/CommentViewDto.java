package com.anna.sbb.viewDto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CommentViewDto {

    // *** Controller <=== Database ***

    private Long id;
    private String commentor;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder
    public CommentViewDto(Long id, String commentor, String content,
                          LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.commentor = commentor;
        this.content = content;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;

        // 로깅 추가
        log.info("CommentViewDto @Builder created: id={}, commentor={}, createdDate={}, lastModifiedDate={}",
                id, commentor, createdDate, lastModifiedDate);
    }
}
