package com.anna.sbb.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.ArticleMapper;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.viewDto.ArticleViewDto;
import org.springframework.data.domain.Sort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    
    // *** Controller <=== Database ***
    // *** ArticleViewDto <=== Article ***
    @Deprecated
	public List<ArticleViewDto> getAllArticles() {
		return this.articleRepository.findAll().stream()
				.map(articleMapper::toViewDto)
				.toList();
	}
	
    // *** Controller <=== Database ***
	// *** ArticleViewDto <=== Article ***
    public ArticleViewDto getArticleById(Long id) {
    	return this.articleRepository.findById(id)
    			.map(articleMapper::toViewDto)
    			.orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException : " + id));
    }

    
    // *** Controller <=== Database ***
    // *** ArticleViewDto <=== Article ***
    public Page<ArticleViewDto> getPage(int pageNumber) {
    	return this.articleRepository.findAll(createPageRequest(pageNumber))
    			.map(this.articleMapper::toViewDto);
    }
    
    private PageRequest createPageRequest(int pageNumber) {
    	int pageSize = 10;
    	Sort sort = Sort.by(
    		Sort.Order.desc("createdDate")
    	);
    	return PageRequest.of(pageNumber, pageSize, sort);
    }
    
    // *** Controller ===> Database ***
    // *** ArticleSubmitForm ===> Article ***
    @Transactional
    public ArticleViewDto createArticle(ArticleSubmitForm articleSubmitForm, Long userId) { 
    	SiteUser siteUser = this.userRepository.findById(1L)
    			.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, siteUser);
    	return this.articleMapper.toViewDto(this.articleRepository.save(newArticle));
    }
    
    // *** Controller ===> Database ***
    // *** ArticleSubmitForm ===> Article
    @Transactional
    public ArticleViewDto modifyArticle(Long id, ArticleSubmitForm modifiedForm) {
    	 return articleRepository.findById(id)
    			 .map(articleEntity -> {
    				 articleEntity.update(modifiedForm.getTitle(), modifiedForm.getContent());
    				 return articleRepository.save(articleEntity);
    			 })
    			 .map(articleMapper::toViewDto)
    			 .orElseThrow(() -> new IllegalArgumentException("IllegalArgumentException : " + id));
    }
    
 // *** Controller ===> Database ***
    @Transactional
    public void deleteArticleById(Long id) {
    	if(!this.articleRepository.existsById(id)) {
    		throw new IllegalArgumentException("IllegalArgumentException : " + id);
    	} 
    	this.articleRepository.deleteById(id);
    }
    
}
