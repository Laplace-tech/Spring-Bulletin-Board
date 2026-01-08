package com.anna.sbb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.anna.sbb.domain.Article;
import com.anna.sbb.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class BlogApiControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private ArticleRepository articleRepostiroy;
	
//	@BeforeEach
//	void test() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//				.build();
//		this.articleRepostiroy.deleteAll();
//	}

//	@DisplayName("createArticle : 글 추가 성공")
//	@Test
//	public void createArticleTest() throws Exception {
//		
//		// given
//		final String url = "/articles/create";
//		final String title = "Title";
//		final String content = "content";
//		
//		final ArticleDto articleDto = ArticleDto.builder()
//											.title(title)
//											.content(content)
//											.build();		
//		
//		// 객체 JSON으로 직렬화
//		final String requestBody = objectMapper.writeValueAsString(articleDto);
//		
//		// when
//		ResultActions result = mockMvc.perform(post(url)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(requestBody));
//		
//		// then 
//		result.andExpect(status().isCreated());
//		
//		List<Article> articles = this.articleRepostiroy.findAll();
//		
//		assertThat(articles.size()).isEqualTo(1);
//		assertThat(articles.get(0).getTitle()).isEqualTo(title);
//		assertThat(articles.get(0).getContent()).isEqualTo(content);
//	}
//
//	@DisplayName("findAllArticle : 글 조회 성공")
//	@Test
//	public void findAllArticleTest() throws Exception {
//		//given 
//		final String url = "/articles";
//		final String title = "TestTitle";
//		final String content = "TestContent";
//		
//		this.articleRepostiroy.save(Article.builder()
//						.title(title)
//						.content(content)
//						.build());
//		
//		//when 
//		final ResultActions resultActions = mockMvc.perform(get(url)
//				.accept(MediaType.APPLICATION_JSON));
//		
//		//then 
//		resultActions
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$[0].content").value(content))
//			.andExpect(jsonPath("$[0].title").value(title));
//		
//	}
//	
//	@DisplayName("findArticleById : 특정 글 조회 성공")
//	@Test
//	public void findArticleByIdTest() throws Exception {
//		//given
//		final String url = "/articles/{id}";
//		final String title = "title";
//		final String content = "content";
//		
//		Article savedArticle = this.articleRepostiroy.save(Article.builder()
//				.title(title)
//				.content(content)
//				.build());
//		
//		//when
//		final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));
//		
//		
//		//then
//		resultActions
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.content").value(content))
//			.andExpect(jsonPath("$.title").value(title));
//		
//	}
//	
//	@DisplayName("deleteArticle : 글 삭제 성공")
//	@Test
//	public void deleteArticle() throws Exception {
//		// given
//		final List<Article> articleList = this.articleRepostiroy.findAll();
//		
//		final int size = articleList.size();
//		
//		final String url = "/articles/{id}";
//		final String title = "title";
//		final String content = "content";
//
//		Article savedArticle = this.articleRepostiroy.save(Article.builder()
//				.title(title)
//				.content(content)
//				.build());
//
//		//when
//		mockMvc.perform(delete(url, savedArticle.getId()))
//				.andExpect(status().isNoContent());
//		
//		//then
//		assertThat(size).isEqualTo(articleList.size());
//		
//	}
}



