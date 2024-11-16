package com.anna.sbb.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.viewDto.ArticleViewDto;
import com.anna.sbb.viewDto.CommentViewDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ArticleMapper {
	
//	ArticleSubmitForm ------------\
//	  							   ------------\
//											    ---> Article
//	  							   -----------------/
//	ArticleViewDto <--------------/
	
	private final CommentMapper commentMapper;
	
	// *** Controller ===> Database ***
	// *** Convert "ArticleSubmitForm" to "Article" ***
	public Article toEntity(ArticleSubmitForm articleSubmitForm, SiteUser author) {
		
		System.err.println("Article Mapper - toEntity");
		
		return Article.builder()
				.title(articleSubmitForm.getTitle())
				.content(articleSubmitForm.getContent())
				.author(author)
				.build();
	}


	// *** Controller <=== Database 
	// *** Convert "Article" to "ArticleViewDto" ***
	public ArticleViewDto toViewDto(Article articleEntity) {

		System.err.println("Article Mapper - toViewDto");
		
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
	
	public ArticleSubmitForm toSubmitForm(Article article) {
		
		System.err.println("Article Mapper - toSubmitForm");
		
		return ArticleSubmitForm.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.build();
	}
    
}
