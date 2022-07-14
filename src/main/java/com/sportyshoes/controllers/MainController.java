package com.sportyshoes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage(Model model) {
		model.addAttribute("pageTitle", "Home - Sporty Shoes");
		return "index";	
	}
	
	@GetMapping("/login")
	public String viewLoginPage(Model model) {
		model.addAttribute("pageTitle", "Login Page");
		return"login";
	}
}
