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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

	private static final int PAGE_SIZE = 2;
	
    private final ArticleRepository articleRepository;
    
    private final UserService userService;
    
    private final ArticleMapper articleMapper;
    
    Article findArticleById(Long articleID) {
    	return this.articleRepository.findById(articleID)
    		.orElseThrow(() -> new IllegalArgumentException("Article Not Found with ID " + articleID));
    }
    
	public List<ArticleViewDto> readAllArticlesView() {
		return this.articleRepository.findAll().stream() // @Entity를 가져와서 Stream 으로 변환
					.map(articleMapper::toViewDto) // 그대로 보여주기 대신, ViewDTO로 변환하여 반환한다.
					.toList();
	}
    
	public ArticleSubmitForm readArticleSubmitFormById(Long articleID) {
		Article submittedArticle = this.findArticleById(articleID);
    	return this.articleMapper.toSubmitForm(submittedArticle);
    }
	
    public ArticleViewDto readArticleViewById(Long articleID) {
    	Article detailArticle = this.findArticleById(articleID);
    	return this.articleMapper.toViewDto(detailArticle);
    }

    public Page<ArticleViewDto> readArticleViewPage(int pageNumber) {
    	
    	System.out.println("ArticleSerivce : readArticleViewPage(" + pageNumber + ")");
    	
    	Pageable pageable = createPageRequest(pageNumber); 
    	return this.articleRepository.findAll(pageable) // articleRepository의 findAll()로 @Entity를 가져와서
    				.map(articleMapper::toViewDto); // 그대로 보여주기 대신, ArticleViewDto로 변환하여 띄운다.
    }
    
    private PageRequest createPageRequest(int pageNumber) {
    	Sort sort = Sort.by(
    		Sort.Order.desc("createdDate")
    	);
    	return PageRequest.of(pageNumber, PAGE_SIZE, sort);
    }
    
    public ArticleViewDto createArticle(String userName, ArticleSubmitForm articleSubmitForm) { 
    	
    	System.out.println("ArticleService : createArticle");
    	
    	SiteUser author = this.userService.findUserByUserName(userName);
    	Article newArticle = this.articleMapper.toEntity(articleSubmitForm, author);
    	
    	return this.articleMapper.toViewDto(articleRepository.save(newArticle));
    }
    
    public ArticleViewDto updateArticle(Long articleID, ArticleSubmitForm modifiedForm) {
    	Article modifiedArticle = findArticleById(articleID);
    	modifiedArticle.update(modifiedForm.getTitle(), modifiedForm.getContent());
    	
    	articleRepository.save(modifiedArticle);
    	return this.articleMapper.toViewDto(modifiedArticle);
    }
 
    public void deleteArticle(Long articleID) {
    	Article articleToDelete = this.findArticleById(articleID);
    	this.articleRepository.delete(articleToDelete);
    }
    
	public boolean checkAuthorPermission(Long articleID, Principal principal) {
		String articleAuthor = this.findArticleById(articleID).getAuthor().getUserName();
		return principal.getName().equals(articleAuthor);
	}
	
//	@PreAuthorize("hasRole('ADMIN') or #userDetails.username == @articleService.findAuthorById(#articleID)")
//	public boolean checkAuthorPermission(Long articleID, UserDetails userDetails) {
//		return true;
//	}
	
}
