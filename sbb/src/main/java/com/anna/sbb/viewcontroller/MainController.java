package com.anna.sbb.viewcontroller;

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
	
}
