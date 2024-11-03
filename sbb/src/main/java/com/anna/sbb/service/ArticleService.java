package com.anna.sbb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.anna.sbb.ArticleNotFoundException;
import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.ArticleMapper;
import com.anna.sbb.mapper.CommentMapper;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.repository.UserRepository;
import com.anna.sbb.viewDto.ArticleViewDto;

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
	public List<ArticleViewDto> findAllArticles() {
		return this.articleRepository.findAll().stream()
				.map(articleMapper::toViewDto)
				.toList();
	}
	
    // *** Controller <=== Database ***
	// *** ArticleViewDto <=== Article ***
    public ArticleViewDto findArticleById(Long id) {
    	return this.articleRepository.findById(id)
    			.map(articleMapper::toViewDto)
    			.orElseThrow(() -> new ArticleNotFoundException(id));
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
    			 .orElseThrow(() -> new ArticleNotFoundException(id));
    }
    
 // *** Controller ===> Database ***
    @Transactional
    public void deleteArticleById(Long id) {
    	if(!this.articleRepository.existsById(id)) {
    		throw new ArticleNotFoundException(id);
    	} 
    	this.articleRepository.deleteById(id);
    }
    
}
