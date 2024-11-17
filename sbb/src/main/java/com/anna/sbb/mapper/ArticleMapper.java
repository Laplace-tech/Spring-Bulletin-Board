package com.anna.sbb.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.*;
import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.CommentViewDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class ArticleMapper {
	
	private final CommentMapper commentMapper;
	
	// *** Controller ===> Database ***
	// *** Convert "ArticleSubmitForm" to "Article" ***
	public Article toEntity(ArticleSubmitForm articleSubmitForm, SiteUser author) {
		log.info("Mapping ArticleSubmitForm to Article entity");
		
		Article article = Article.builder()
				.title(articleSubmitForm.getTitle())
				.content(articleSubmitForm.getContent())
				.author(author)
				.build();

		log.info("Mapped Article entity: {}", article);
		return article;
	}

	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleViewDto" ***
	public ArticleViewDto toViewDto(Article articleEntity) {
		log.info("Mapping Article entity to ArticleViewDto");
		
		List<CommentViewDto> commentViewDtoList = articleEntity.getCommentList().stream()
				.map(this.commentMapper::toViewDto)
				.toList();

		ArticleViewDto viewDto = ArticleViewDto.builder()
                .id(articleEntity.getId())
                .author(articleEntity.getAuthor().getUserName())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .commentList(commentViewDtoList)
                .createdDate(articleEntity.getCreatedDate())
                .lastModifiedDate(articleEntity.getLastModifiedDate())
                .build();

		log.info("Mapped ArticleViewDto: {}", viewDto);
		return viewDto;
	}
	
	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleSubmitForm" ***
	public ArticleSubmitForm toSubmitForm(Article article) {
		log.info("Mapping Article entity to ArticleSubmitForm");

		ArticleSubmitForm submitForm = ArticleSubmitForm.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.build();

		log.info("Mapped ArticleSubmitForm: {}", submitForm);
		return submitForm;
	}
}
