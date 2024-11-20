package com.anna.sbb.service;

import java.security.Principal;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

	private static final int PAGE_SIZE = 10;
	
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final ArticleMapper articleMapper;
    
    
    // *** Helper Methods ***
    
    Article findArticleById(Long articleID) {
    	return this.articleRepository.findById(articleID)
    		.orElseThrow(() -> new IllegalArgumentException(
    				String.format("Article with ID [%d] not found.", articleID)));
    }
    
	private PageRequest createPageRequest(int requestPageNum) {
		return PageRequest.of(requestPageNum, PAGE_SIZE, Sort.by(Sort.Order.desc("createdDate")));
	}
	
	
    // *** CRUD Operations *** 
        
	public List<ArticleViewDto> getAllArticleViewDtos() {
		
		log.info("[getAllArticleViewDtos] >> Fetching all Articles as ArticleViewDto.");
		
		return this.articleRepository.findAll().stream()
				.map(this.articleMapper::toViewDto)
//				.map(entity -> this.articleMapper.toViewDto(entity))
				.toList();
	}

	public Page<ArticleViewDto> getArticleViewPage(int requestPageNum) {

		log.info("[getArticleViewPage] >> Fetching paginated ArticleViewDtos for page [{}].", requestPageNum);

		Pageable pageable = this.createPageRequest(requestPageNum);

		return this.articleRepository.findAll(pageable)
				.map(this.articleMapper::toViewDto);
	}

	public ArticleViewDto getArticleViewDtoById(Long articleID) {
		
		log.info("[getArticleViewDtoById] >> Fetching ArticleViewDto for Article ID [{}].", articleID);
		
		Article article = this.findArticleById(articleID);
		return this.articleMapper.toViewDto(article);
	}

	public ArticleSubmitForm getArticleSubmitFormById(Long articleID) {

		log.info("[getArticleSubmitFormById] >> Fetching ArticleSubmitForm for Article ID [{}].", articleID);
		
		Article article = this.findArticleById(articleID);
		return this.articleMapper.toSubmitForm(article);
	}
  
    public ArticleViewDto createArticle(String userName, ArticleSubmitForm articleSubmitForm) {
    	
       	log.info("[createArticle] >> Creating new Article for user [{}].", userName);
       	
    	SiteUser siteUser = this.userService.findUserByUserName(userName);
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, siteUser);
    	Article savedArticle = this.articleRepository.save(newArticle);
    	
    	log.info("[createArticle] >> Created new Article with ID [{}].", savedArticle.getId());
    	
    	return this.articleMapper.toViewDto(savedArticle);
    }
  
	public ArticleViewDto updateArticle(Long articleID, ArticleSubmitForm modifiedArticleForm) {

		log.info("[updateArticle] >> Updating Article with ID [{}].", articleID);
		
		Article articleToUpdate = this.findArticleById(articleID);
		Article modifiedArticle = articleToUpdate
				.updateArticle(modifiedArticleForm.getTitle(), modifiedArticleForm.getContent());
		
		this.articleRepository.save(modifiedArticle);
		
		log.info("[updateArticle] >> Updated Article with ID [{}].", modifiedArticle.getId());

		return this.articleMapper.toViewDto(modifiedArticle);
	}
    
    public void deleteArticle(Long articleID) {
    	
    	log.info("[deleteArticle] >> Deleting Article with ID [{}].", articleID);
    	
    	this.articleRepository.delete(this.findArticleById(articleID));
    	
    	log.info("[deleteArticle] >> Deleted Article with ID [{}].", articleID);
    }
    
    public boolean hasAuthorPermission(Long articleID, Principal principal) {
    	
    	log.info("[hasAuthorPermission] >> Checking author permission for Article ID [{}] and user [{}].",
    			articleID, principal.getName());
    	
    	String articleAuthorName = this.findArticleById(articleID).getAuthor().getUserName();
    	boolean hasPermission = principal.getName().equals(articleAuthorName);
    	
    	log.info("[hasAuthorPermission] >> Permission check result : [{}].", hasPermission);
    	
    	return hasPermission;
    }
    
}
