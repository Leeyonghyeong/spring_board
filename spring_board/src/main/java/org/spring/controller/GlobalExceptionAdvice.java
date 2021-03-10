package org.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// 모든 controller ERROR 처리
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
