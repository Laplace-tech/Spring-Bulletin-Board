package com.anna.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anna.sbb.domain.Article;
import com.anna.sbb.repository.ArticleRepository;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Test
	void test() {
		Article article = Article.builder()
                .title("testTitle " + 1) // 각 타이틀에 인덱스를 추가하여 유니크하게 만듭니다.
                .content("testContent " + 1) // 각 콘텐츠에 인덱스를 추가하여 유니크하게 만듭니다.
                .build();
		
		this.articleRepository.save(article);
		
//	    for (int i = 0; i < 50; i++) {
//	        Article article = Article.builder()
//	                            .title("testTitle " + i) // 각 타이틀에 인덱스를 추가하여 유니크하게 만듭니다.
//	                            .content("testContent " + i) // 각 콘텐츠에 인덱스를 추가하여 유니크하게 만듭니다.
//	                            .build();
//	        
//	        this.articleRepository.save(article);    
//	    }
	}

	
}

