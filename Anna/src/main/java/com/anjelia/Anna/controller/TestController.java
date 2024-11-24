package com.anjelia.Anna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Controller
public class TestController {
	
	  // "/hello-mvc" 경로로 접근 시 처리
    @GetMapping("/hello-mvc")
    public String viewHello(@RequestParam(value = "data", defaultValue = "DEFAULT_VALUE") String data, Model model) {
        model.addAttribute("data", data);
        return "test";  // "test"는 Thymeleaf 템플릿 파일
    }
    
    @GetMapping("hello-api")
    @ResponseBody
    public String helloString(@RequestParam(value = "data", defaultValue = "Default_Data") String data) {
    	return String.format("Hello %s", data);
    }
    
    @GetMapping("/hello-A")
    @ResponseBody
    public A viewA() {
    	return new A("아 씨발", "휴가가고싶다");
    }
    
    @AllArgsConstructor
    @Getter
    static class A {
    	private String str1;
    	private String str2;
    }
}
