package com.anna.sbb.mapper;

import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.CommentViewDto;
import org.springframework.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommentMapper {

    // *** Controller ===> Database ***
    // *** Convert "CommentSubmitForm" to "Comment" ***
    public Comment toEntity(CommentSubmitForm commentSubmitForm, Article article, SiteUser commentor) {
        log.info("Mapping CommentSubmitForm to Comment. CommentSubmitForm: {}, Article: {}, Commentor: {}",
                 commentSubmitForm, 
                 article != null ? article.getId() : "null",
                 commentor != null ? commentor.getUserName() : "null");

        Comment comment = Comment.builder()
                .article(article)
                .commentor(commentor)
                .content(commentSubmitForm.getContent())
                .build();

        log.info("Mapped Comment entity: {}", comment);
        return comment;
    }

    // *** Controller <=== Database ***
    // *** Convert "Comment" to "CommentViewDto" ***
    public CommentViewDto toViewDto(Comment commentEntity) {
        log.info("Mapping Comment to CommentViewDto. Comment: {}", commentEntity);

        CommentViewDto commentViewDto = CommentViewDto.builder()
                .id(commentEntity.getId())
                .commentor(commentEntity.getCommentor().getUserName())
                .content(commentEntity.getContent())
                .createdDate(commentEntity.getCreatedDate())
                .lastModifiedDate(commentEntity.getLastModifiedDate())
                .build();

        log.info("Mapped CommentViewDto: {}", commentViewDto);
        return commentViewDto;
    }
}
