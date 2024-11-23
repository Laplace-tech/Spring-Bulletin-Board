package com.anna.sbb.service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.mapper.ArticleMapper;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.viewDto.ArticleViewDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

	private static final int PAGE_SIZE = 2;
	
    private final ArticleRepository articleRepository;
 
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
		
		log.info("[@ArticleService.getAllArticleViewDtos] >> Fetching all Articles as ArticleViewDto.");
		
		return this.articleRepository.findAll().stream()
				.map(this.articleMapper::toViewDto)
//				.map(entity -> this.articleMapper.toViewDto(entity))
				.toList();
	}

	public Page<ArticleViewDto> getArticleViewPage(int requestPageNum) {

		log.info("[@ArticleService.getArticleViewPage] >> Fetching paginated ArticleViewDtos for page [{}].", requestPageNum);

		Pageable pageable = this.createPageRequest(requestPageNum);

		return this.articleRepository.findAll(pageable)
				.map(this.articleMapper::toViewDto);
	}

	public ArticleViewDto getArticleViewDtoById(Long articleID) {
		
		log.info("[@ArticleService.getArticleViewDtoById] >> Fetching ArticleViewDto for Article ID [{}].", articleID);
		
		Article article = this.findArticleById(articleID);
		return this.articleMapper.toViewDto(article);
	}

	public ArticleSubmitForm getArticleSubmitFormById(Long articleID) {

		log.info("[@ArticleService.getArticleSubmitFormById] >> Fetching ArticleSubmitForm for Article ID [{}].", articleID);
		
		Article article = this.findArticleById(articleID);
		return this.articleMapper.toSubmitForm(article);
	}
  
	public ArticleViewDto getArticleViewDtoByComment(Long commentID, CommentService commentService) {
		Article article = commentService.findCommentById(commentID).getArticle();
		return this.articleMapper.toViewDto(article);
	}
	
    public ArticleViewDto createArticle(ArticleSubmitForm articleSubmitForm, String userName, UserService UserService) {
    	
       	log.info("[@ArticleService.createArticle] >> Creating new Article for user [{}].", userName);
    	
       	SiteUser author = UserService.findUserByUserName(userName);
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, author);
    	newArticle = this.articleRepository.save(newArticle);
    	
    	log.info("[@ArticleService.createArticle] >> Created new Article with ID [{}].", newArticle.getId());
    	
    	return this.articleMapper.toViewDto(newArticle);
    }
  
	public ArticleViewDto updateArticle(Long articleID, ArticleSubmitForm modifiedArticleForm) {

		log.info("[@ArticleService.updateArticle] >> Updating Article with ID [{}].", articleID);
		
		Article articleToUpdate = this.findArticleById(articleID);
		Article modifiedArticle = articleToUpdate
				.updateArticle(modifiedArticleForm.getTitle(), modifiedArticleForm.getContent());
		
		this.articleRepository.save(modifiedArticle);
		
		log.info("[@ArticleService.updateArticle] >> Updated Article with ID [{}].", modifiedArticle.getId());

		return this.articleMapper.toViewDto(modifiedArticle);
	}
    
    public void deleteArticle(Long articleID) {
    	
    	log.info("[@ArticleService.deleteArticle] >> Deleting Article with ID [{}].", articleID);
    	
    	this.articleRepository.delete(this.findArticleById(articleID));
    	
    	log.info("[@ArticleService.deleteArticle] >> Deleted Article with ID [{}].", articleID);
    }
    
    public boolean hasAuthorPermission(Long articleID, Principal principal) {
    	
    	log.info("[@ArticleService.hasAuthorPermission] >> Checking author permission for Article ID [{}] and User [{}].",
    			articleID, principal.getName());
    	
    	String articleAuthorName = this.findArticleById(articleID).getAuthor().getUserName();
    	boolean hasPermission = principal.getName().equals(articleAuthorName);
    	
    	log.info("[@ArticleService.hasAuthorPermission] >> Permission check result : [{}].", hasPermission);
    	
    	return hasPermission;
    }
    
}
