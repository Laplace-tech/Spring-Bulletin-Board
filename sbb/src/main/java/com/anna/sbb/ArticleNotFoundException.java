package com.anna.sbb;

public class ArticleNotFoundException extends RuntimeException{

	public ArticleNotFoundException(Long id) {
		super("Article with id : " + id + " not found.");
	}
	
}
