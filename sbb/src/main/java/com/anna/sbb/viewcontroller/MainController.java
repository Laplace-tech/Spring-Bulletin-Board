package com.anna.sbb.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.*;
@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "redirect:/articles";
	}
	
}
