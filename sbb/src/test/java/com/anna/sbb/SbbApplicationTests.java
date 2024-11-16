package com.anna.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anna.sbb.createDto.ArticleSubmitForm;
import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.createDto.UserRegistrationForm;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.service.CommentService;
import com.anna.sbb.service.UserService;


@SpringBootTest

class SbbApplicationTests {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public SbbApplicationTests(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }
    
	@Test
	void test() {

		UserRegistrationForm testUser = new UserRegistrationForm();
		testUser.setUserName("Anna");
		testUser.setPassword("28482848a!");
		testUser.setCheckPassword("28482848a!");
		testUser.setEmail("add28482848@gmail.com");

		this.userService.createUser(testUser);

		////////////////////////////////////////////////////////////

		for (int i = 0; i < 50; i++) {
			ArticleSubmitForm testArticle = new ArticleSubmitForm();
			testArticle.setTitle("Test Title : " + i);
			testArticle.setContent("Test Content : " + i);

			this.articleService.createArticle("Anna", testArticle);
		}

		////////////////////////////////////////////////////////////

		for (int i = 1; i <= 50; i++) {
			for (int j = 1; j <= 2; j++) {
				CommentSubmitForm testComment = new CommentSubmitForm();
				testComment.setContent(String.format("Test Comment : %d-%d", i, j));
				this.commentService.createComment((long)i, 1L, testComment);
			}
		}

	}

}
