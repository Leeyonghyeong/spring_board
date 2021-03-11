package org.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	
	@RequestMapping(value="/login")
	public String loginPage() throws Exception {
		
		return "user/login";
	}
	
	@RequestMapping(value="/denied_page")
	public String deniedPage() throws Exception {
		
		return "denied_page";
	}

}
