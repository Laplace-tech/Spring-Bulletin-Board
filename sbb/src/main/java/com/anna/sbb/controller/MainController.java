package com.anna.sbb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Getter;
import lombok.Setter;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() { 
		return "redirect:/articles";
	}
	
	@GetMapping("/thymeleaf/example")
	public String thymeleafExample(Model model) {
		Person person = new Person();
		person.id = 1L;
		person.name = "Anna";
		person.age = 21;
		person.hobbyList = List.of("Listen for music", "play coding", "imagination for future");
		
		model.addAttribute("person", person);
		model.addAttribute("today", LocalDateTime.now());
		
		return "example";
	}
	
	@Setter
	@Getter
	class Person {
		private Long id;
		private String name;
		private int age;
		private List<String> hobbyList;
	}
	
}
