package com.anna.sbb.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anna.sbb.createDto.CommentSubmitForm;
import com.anna.sbb.domain.Article;
import com.anna.sbb.service.ArticleService;
import com.anna.sbb.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comment")
@RequiredArgsConstructor
@RestController
public class CommentController {

	private final ArticleService articleService;
	private final CommentService commentService;
	
	
//	@PostMapping("/create/{id}")
//	public String createComment(@PathVariable("id") Long id, @Valid CommentSubmitForm commentSubmitForm,
//			BindingResult bindingResult) {
//		
//		
//		
//	}

}
