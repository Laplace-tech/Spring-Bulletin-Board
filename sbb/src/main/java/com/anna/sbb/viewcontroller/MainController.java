package com.anna.sbb.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "redirect:/articles";
	}
	
	// Use a distinct path for the dynamic route
	@GetMapping("/hello-mvc")
	public String viewHello(@RequestParam(value = "data", defaultValue = "DEFAULT_VALUE") String data, Model model) {
		model.addAttribute("data", data);
		return "test";  // "test" is your Thymeleaf template
	}
	
}
