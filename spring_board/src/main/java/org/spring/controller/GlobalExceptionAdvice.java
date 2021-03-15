package org.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
* controller 에서 발생하는 모든 에러처리를 위한 클래스
* 
* @author L
*/
@ControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public ModelAndView ExceptionHandler(Exception e) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/error_handler");
		mav.addObject("exception", e);
		
		return mav;
	}
}
