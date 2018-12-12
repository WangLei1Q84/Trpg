package com.brett.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brett.service.userService;

@Controller
public class UserController {

	@Autowired
	public userService userservice;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
}
