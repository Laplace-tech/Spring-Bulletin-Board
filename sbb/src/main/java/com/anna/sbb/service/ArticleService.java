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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

	private static final int PAGE_SIZE = 10;
	
    private final ArticleRepository articleRepository;
    
    private final UserService userService;
    
    private final ArticleMapper articleMapper;
    
    Article findArticleById(Long articleID) {
    	return this.articleRepository.findById(articleID)
    		.orElseThrow(() -> new IllegalArgumentException("Article Not Found with ID " + articleID));
    }
    
	public List<ArticleViewDto> readAllArticlesView() {
		return this.articleRepository.findAll().stream() // @Entity를 가져와서
					.map(articleMapper::toViewDto) // 그대로 보여주기 대신, ViewDTO로 변환하여 반환한다.
					.toList();
	}
    
    public ArticleViewDto readArticleViewById(Long articleID) {
    	return this.articleMapper.toViewDto(findArticleById(articleID));
    }

    public Page<ArticleViewDto> readArticleViewPage(int pageNumber) {
    	Pageable pageable = createPageRequest(pageNumber); 
    	return this.articleRepository.findAll(pageable)
    				.map(articleMapper::toViewDto);
    }
    
    public ArticleSubmitForm readArticleSubmitFormById(Long articleID) {
    	return this.articleMapper.toSubmitForm(findArticleById(articleID));
    }
    		
    
    private PageRequest createPageRequest(int pageNumber) {
    	Sort sort = Sort.by(
    		Sort.Order.desc("createdDate")
    	);
    	return PageRequest.of(pageNumber, PAGE_SIZE, sort);
    }
    
    public ArticleViewDto createArticle(Long userID, ArticleSubmitForm articleSubmitForm) { 
    	SiteUser author = this.userService.findUserById(userID);
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, author);
    	return this.articleMapper.toViewDto(articleRepository.save(newArticle));
    }
    
    public ArticleViewDto updateArticle(Long articleID, Long userID, ArticleSubmitForm modifiedForm) {
    	Article modifiedArticle = findArticleById(articleID);
    	
    	if(modifiedArticle.getAuthor().getId() != userID) {
    		// fuck you
    	}
    	
    	modifiedArticle.update(modifiedForm.getTitle(), modifiedForm.getContent());
    	articleRepository.save(modifiedArticle);
    	return this.articleMapper.toViewDto(modifiedArticle);
    }
 
    public void deleteArticle(Long articleID, Long userID) {
    	Article articleToDelete = this.findArticleById(articleID);
    	
    	if(articleToDelete.getAuthor().getId() != userID) {
    		// fuck you
    	}
    	
    	this.articleRepository.delete(articleToDelete);
    }
    
}
