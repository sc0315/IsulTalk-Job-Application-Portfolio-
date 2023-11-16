package com.isul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/")
public class MainController {
	
	@RequestMapping("/")
	public String check() {
		System.out.println("메인");
		return "main/main";
	}
}
