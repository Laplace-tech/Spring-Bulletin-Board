package com.anna.sbb.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
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

		log.info("[toEntity] >> Mapping ArticleSubmitForm to Article entity. SubmitForm title : {}, Author : {}",
				articleSubmitForm.getTitle(), author.getUserName());

		Article article = Article.builder()
				.title(articleSubmitForm.getTitle())
				.content(articleSubmitForm.getContent())
				.author(author).build();

		log.info("[toEntity] >> Successfully mapped Article entity. Entity Title : {}, Author : {}", 
				article.getTitle(), author.getUserName());

		return article;
	}

	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleViewDto" ***
	public ArticleViewDto toViewDto(Article articleEntity) {
		
		log.info("[toViewDto] >> Mapping Article entity to ArticleViewDto. Entity Article ID : {}, Title : {}", 
				articleEntity.getId(), articleEntity.getTitle());
		
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

		log.info("[toViewDto] >> Successfully mapped ArticleViewDto. ViewDto ArticleID : {}, Title : {}",
				viewDto.getId(), viewDto.getTitle());
		
		return viewDto;
	}
	
	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleSubmitForm" ***
	public ArticleSubmitForm toSubmitForm(Article article) {

		log.info("[toSubmitForm] >> Mapping Article entity to ArticleSubmitForm. Article ID : {}, Title : {}",
				article.getId(), article.getTitle());
		
		ArticleSubmitForm submitForm = ArticleSubmitForm.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.build();

		log.info("[toSubmitForm] >> Successfully mapped ArticleSubmitForm. ID : {}, Title : {}", 
				submitForm.getId(), submitForm.getTitle());
		
		return submitForm;
	}
}
