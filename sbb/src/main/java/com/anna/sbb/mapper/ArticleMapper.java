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

//		log.info("[toEntity] >> Mapping ArticleSubmitForm to Article. SubmitForm title : {}, Author : {}",
//				articleSubmitForm.getTitle(), author.getUserName());

		Article article = Article.builder()
				.title(articleSubmitForm.getTitle())
				.content(articleSubmitForm.getContent())
				.author(author).build();

		return article;
	}

	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleViewDto" ***
	public ArticleViewDto toViewDto(Article articleEntity) {
		
//		log.info("[toViewDto] >> Mapping Article to ArticleViewDto. Article ID : {}, Title : {}", 
//				articleEntity.getId(), articleEntity.getTitle());
		
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

		return viewDto;
	}
	
	// *** Controller <=== Database ***
	// *** Convert "Article" to "ArticleSubmitForm" ***
	public ArticleSubmitForm toSubmitForm(Article article) {

//		log.info("[toSubmitForm] >> Mapping Article to ArticleSubmitForm. Article ID : {}, Title : {}",
//				article.getId(), article.getTitle());
		
		ArticleSubmitForm submitForm = ArticleSubmitForm.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.build();

		return submitForm;
	}
}
