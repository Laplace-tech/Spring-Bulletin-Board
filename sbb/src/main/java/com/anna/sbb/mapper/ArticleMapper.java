package com.anna.sbb.mapper;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.CommentViewDto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ArticleMapper {
	
	private final CommentMapper commentMapper;
	
	// *** Controller ===> Database ***
	// *** Convert "ArticleSubmitForm" to "Article" ***
	public Article toEntity(ArticleSubmitForm articleSubmitForm, SiteUser author) {
		return Article.builder()
				.title(articleSubmitForm.getTitle())
				.content(articleSubmitForm.getContent())
				.author(author)
				.build();
	}


	// *** Controller <=== Database 
	// *** Convert "Article" to "ArticleViewDto" ***
	public ArticleViewDto toViewDto(Article articleEntity) {

		List<CommentViewDto> commentViewDtoList = articleEntity.getCommentList().stream()
				.map(this.commentMapper::toViewDto).toList();

		return ArticleViewDto.builder()
                .id(articleEntity.getId())
                .author(articleEntity.getAuthor().getUserName())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .commentList(commentViewDtoList)
                .createdDate(articleEntity.getCreatedDate())
                .lastModifiedDate(articleEntity.getLastModifiedDate())
                .build();
	}
	
    
}
