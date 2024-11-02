package com.anna.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anna.sbb.domain.Article;
import com.anna.sbb.domain.Comment;
import com.anna.sbb.domain.SiteUser;
import com.anna.sbb.repository.ArticleRepository;
import com.anna.sbb.repository.CommentRepository;
import com.anna.sbb.repository.UserRepository;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	void test() {
	
		SiteUser user = SiteUser.builder()
							.userName("Anna")
							.password("2848")
							.email("af@mil")
							.build();
		
		this.userRepository.save(user);
		
	    for (int i = 1; i < 50; i++) {
	        Article article = Article.builder()
	                            .title("testTitle " + i) // 각 타이틀에 인덱스를 추가하여 유니크하게 만듭니다.
	                            .content("testContent " + i) // 각 콘텐츠에 인덱스를 추가하여 유니크하게 만듭니다.
	                            .author(user)
	                            .build();
	        
	        this.articleRepository.save(article);    
	    }
	    
	    for (int i = 1; i < 50; i++) {
	        Comment comment = Comment.builder()
	        		.article(this.articleRepository.findById((long)i).get())
	        		.commentor(user)
	        		.content("Comment" + i)
	        		.build();
	        
	        this.commentRepository.save(comment);
	    }
	}

	
}

