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

/**
* Spring Security 를 위한 로그인 처리 클래스
* 
* @author L
*/
@Controller
public class UserController {
	
	@Inject
	private UserService service;
	
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	/**
	 * 커스텀한 로그인 페이지로 이동을 위한 함수
	 * 
	 */
	@RequestMapping(value="/login")
	public String loginPage(Model model) throws Exception {
		
		return "user/login";
	}
	
	/**
	 * 권한이 없는 사용자가 페이지 요청을 할때 보여주는 페이지 함수
	 * 현재 사용하지 않음 사용하기 위해서 security xml 수정해야함.
	 * 
	 */
	@RequestMapping(value="/denied_page")
	public String deniedPage() throws Exception {
		
		return "denied_page";
	}
	
	/**
	 * 회원 가입을 위한 페이지
	 * 
	 * @param reqw  비밀번호 확인을 위해 재입력 받은 비밀번호 파라미터
	 * @param vo  유저 vo객체에 유저 정보를 담기 위한 파라미터
	 */
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinUser(@RequestParam("re-password") String repw, UserDetailVO vo, RedirectAttributes ra) throws Exception {
		
		vo.setPassword(pwEncoder.encode(vo.getPassword()));
		
		service.joinUser(vo);
		
		ra.addFlashAttribute("join", "SUCCESS");
		
		return "redirect:/login";
	}
	
	
	/**
	 * 회원 가입시 사용자가 입력한 아이디가 존재하는지 여부를 확인하는 함수
	 * 
	 * @param userid  사용자가 입력한 아이디를 받아오는 파라미터
	 */
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
