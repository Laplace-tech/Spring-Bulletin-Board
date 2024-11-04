package com.anna.sbb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.ArticleMapper;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.viewDto.ArticleViewDto;
import org.springframework.data.domain.Sort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

	private static final int PAGE_SIZE = 10;
	
    private final ArticleRepository articleRepository;
    
    private final UserService userService;
    
    private final ArticleMapper articleMapper;
    
    public Article findArticleById(Long articleID) {
    	return this.articleRepository.findById(articleID)
    		.orElseThrow(() -> new IllegalArgumentException("Article Not Found with ID " + articleID));
    }
    
    // *** Controller <=== Database ***
    // *** ArticleViewDto <=== Article ***
	public List<ArticleViewDto> readAllArticles() {
		return this.articleRepository.findAll().stream()
					.map(articleMapper::toViewDto)
					.toList();
	}
    
    // *** Controller <=== Database ***
	// *** ArticleViewDto <=== Article ***
    public ArticleViewDto readArticleById(Long articleID) {
    	return this.articleMapper.toViewDto(findArticleById(articleID));
    }

    // *** Controller <=== Database ***
    // *** ArticleViewDto <=== Article ***
    public Page<ArticleViewDto> readPage(int pageNumber) {
    	Pageable pageable = this.createPageRequest(pageNumber);
    	return this.articleRepository.findAll(pageable)
    				.map(articleMapper::toViewDto);
    }
    
    private PageRequest createPageRequest(int pageNumber) {
    	Sort sort = Sort.by(
    		Sort.Order.desc("createdDate")
    	);
    	return PageRequest.of(pageNumber, PAGE_SIZE, sort);
    }
    
    // *** Controller ===> Database ***
    // *** ArticleSubmitForm ===> Article ***
    public ArticleViewDto createArticle(Long userID, ArticleSubmitForm articleSubmitForm) { 
    	SiteUser author = this.userService.findUserById(userID);
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, author);
    	return this.articleMapper.toViewDto(articleRepository.save(newArticle));
    }
    
    // *** Controller ===> Database ***
    // *** ArticleSubmitForm ===> Article
    @Transactional
    public ArticleViewDto updateArticle(Long articleID, ArticleSubmitForm modifiedForm) {
    	Article modifiedArticle = findArticleById(articleID)
   			 .update(modifiedForm.getTitle(), modifiedForm.getContent());
    	articleRepository.save(modifiedArticle);
    	return this.articleMapper.toViewDto(modifiedArticle);
    }
    
    // *** Controller ===> Database ***
    @Transactional
    public void deleteArticleById(Long articleID) {
    	this.articleRepository.delete(findArticleById(articleID));
    }
    
}
