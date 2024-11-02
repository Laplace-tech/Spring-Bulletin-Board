package com.anna.sbb.service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.anna.sbb.ArticleNotFoundException;
import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
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
    
    // *** Controller ===> Database ***
    private final BiFunction<ArticleSubmitForm, SiteUser, Article> convertFormToEntity = (articleSubmitForm, siteUser) 
    		-> Article.builder()
                    .title(articleSubmitForm.getTitle())
                    .content(articleSubmitForm.getContent())
                    .author(siteUser)
                    .build();
    
    // *** Controller <=== Database ***
    private final Function<Article, ArticleViewDto> convertEntityToViewDto = articleEntity -> ArticleViewDto.builder()
                    .id(articleEntity.getId())
                    .author(articleEntity.getAuthor())
                    .title(articleEntity.getTitle())
                    .content(articleEntity.getContent())
                    .commentList(articleEntity.getCommentList())
                    .createdDate(articleEntity.getCreatedDate())
                    .lastModifiedDate(articleEntity.getLastModifiedDate())
                    .build();
    
    
    // *** Controller <=== Database ***
    public List<ArticleViewDto> findAllArticles() {
        return this.articleRepository.findAll().stream().map(convertEntityToViewDto).toList();
    }
    
    // *** Controller <=== Database ***
    public ArticleViewDto findArticleById(Long id) {
        Optional<Article> resultEntity = this.articleRepository.findById(id);
        return this.convertEntityToViewDto
        		.apply(resultEntity.orElseThrow(() -> new ArticleNotFoundException(id)));
    }
    
    // *** Controller ===> Database ***
    @Transactional
    public ArticleViewDto createArticle(ArticleSubmitForm articleSubmitForm) { 
    	
    	/****************************************/
    	SiteUser exampleUser = this.userRepository.findById(1L).get();
    	/****************************************/
    	
        Article newArticle = this.convertFormToEntity.apply(articleSubmitForm, exampleUser);
        return this.convertEntityToViewDto.apply(this.articleRepository.save(newArticle));
    }
    
    @Transactional
    public ArticleViewDto modifyArticle(Long id, ArticleSubmitForm modifiedArticleSubmitForm) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        
        article.update(modifiedArticleSubmitForm.getTitle(), modifiedArticleSubmitForm.getContent());
        return this.convertEntityToViewDto.apply(this.articleRepository.save(article));
    }
    
    @Transactional
    public void deleteArticleById(Long id) {
        this.articleRepository.deleteById(id);
    }
}
