package com.anna.sbb.mapper;

import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.CommentViewDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommentMapper {

    // *** Controller ===> Database ***
    // *** Convert "CommentSubmitForm" to "Comment" ***
    public Comment toEntity(CommentSubmitForm commentSubmitForm, Article article, SiteUser commentor) {
    	
//    	log.info("[toEntity] >> Mapping CommentSubmitForm to Commenmt. Content : {}, Article ID : {}, Commentor : {}",
//    			commentSubmitForm.getContent(), article.getId(), commentor.getUserName());
    	
        Comment comment = Comment.builder()
                .article(article)
                .commentor(commentor)
                .content(commentSubmitForm.getContent())
                .build();

    	return comment;
    }

    // *** Controller <=== Database ***
    // *** Convert "Comment" to "CommentViewDto" ***
    public CommentViewDto toViewDto(Comment commentEntity) {
    	
//    	log.info("[toViewDto] >> Mapping Comment to CommentViewDto. Content : {}, Article ID : {}, Commentor : {}",
//    			commentEntity.getContent(), commentEntity.getArticle().getId(), commentEntity.getCommentor().getUserName());

    	CommentViewDto commentViewDto = CommentViewDto.builder()
                .commentId(commentEntity.getId())
                .commentor(commentEntity.getCommentor().getUserName())
                .content(commentEntity.getContent())
                .articleId(commentEntity.getArticle().getId())
                .createdDate(commentEntity.getCreatedDate())
                .lastModifiedDate(commentEntity.getLastModifiedDate())
                .build();

        return commentViewDto;
    }
    
    // *** Controller <=== Database ***
    // *** Convert "Comment" to "CommentSubmitForm"
    public CommentSubmitForm toSubmitForm(Comment comment) {
    
		CommentSubmitForm submitForm = CommentSubmitForm.builder()
				.id(comment.getId())
				.content(comment.getContent())
				.build();
    
		return submitForm;
    }
}
