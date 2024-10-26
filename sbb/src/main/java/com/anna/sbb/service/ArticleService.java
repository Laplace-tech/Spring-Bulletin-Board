package com.anna.sbb.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.anna.sbb.domain.Article;
import com.anna.sbb.dto.ArticleDto;
import com.anna.sbb.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드 또는 @NonNull이 붙은 필드에 대한 생성자를 자동으로 생성
@Service
public class ArticleService {

	private final ArticleRepository articleRepository;
	
	// Controller <=== Database
	private final Function<Article, ArticleDto> convertEntityToDto = articleEntity -> ArticleDto.builder()
			.id(articleEntity.getId())
			.title(articleEntity.getTitle())
			.content(articleEntity.getContent())
			.build();

	// Controller ===> Database
	private final Function<ArticleDto, Article> convertDtoToEntity = articleDto -> Article.builder()
			.title(articleDto.getTitle())
			.content(articleDto.getContent())
			.build();
	
//	@RequiredArgsConstructor // 생성자를 자동으로 생성
//  *의존성 주입 : 스프링은 ArticleRepository의 @Bean을 찾아서 ArticleService의 생성자에 전달함.
//	public ArticleService(ArticleRepository articleRepository) {
//		this.articleRepository = articleRepository; // 주입된 ArticleRepository를 할당
//	}

	public List<ArticleDto> findAllArticles() {
		return this.articleRepository.findAll().stream().map(convertEntityToDto).toList();
	}

	public ArticleDto findArticleById(Long id) {
		return convertEntityToDto.apply(
				this.articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found " + id)));

	}

	@Transactional
	public ArticleDto createArticle(ArticleDto articleDto) {
		Article newArticle = this.articleRepository.save(convertDtoToEntity.apply(articleDto));
		return this.convertEntityToDto.apply(newArticle);
	}

	@Transactional
	public ArticleDto modifyArticle(Long id, ArticleDto modifiedArticleDto) {
		Article article = this.articleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("not found " + id));

		article.update(modifiedArticleDto.getTitle(), modifiedArticleDto.getContent());
		return this.convertEntityToDto.apply(articleRepository.save(article));
	}

	@Transactional
	public void deleteArticleById(Long id) {
		this.articleRepository.deleteById(id);
	}

}
