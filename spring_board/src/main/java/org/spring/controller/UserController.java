package org.spring.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.service.UserService;
import org.spring.vo.UserDetailVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	
	@Inject
	private UserService service;
	
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@RequestMapping(value="/login")
	public String loginPage(Model model) throws Exception {
		
		return "user/login";
	}
	
	@RequestMapping(value="/denied_page")
	public String deniedPage() throws Exception {
		
		return "denied_page";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinUser(@RequestParam("re-password") String repw, UserDetailVO vo, RedirectAttributes ra) throws Exception {
		
		vo.setPassword(pwEncoder.encode(vo.getPassword()));
		
		service.joinUser(vo);
		
		ra.addFlashAttribute("join", "SUCCESS");
		
		return "redirect:/login";
	}
	
	@ResponseBody
	@RequestMapping(value="/user/idValidate", method=RequestMethod.POST)
	public ResponseEntity<String> idValidate(@RequestBody String userid) throws Exception {
		ResponseEntity<String> entity = null;
		
		try {

			if(service.getUserById(userid) != null) {
				entity = new ResponseEntity<String>("ID is Exist", HttpStatus.OK);
			} else {
				entity = new ResponseEntity<String>("ID is NOT Exist", HttpStatus.OK);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
