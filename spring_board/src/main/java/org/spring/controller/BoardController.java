package org.spring.controller;


import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.paging.PageMaker;
import org.spring.searching.SearchCriteria;
import org.spring.service.BoardService;
import org.spring.vo.BoardVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	// 모든 게시물 보기 및 검색 조건에 맞는 게시물 보기
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String board(SearchCriteria scri, Model model) throws Exception {
		logger.info("----------------listAll----------------");
		logger.info(scri.toString());
		
		model.addAttribute("list", service.listCriteria(scri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.totalCount(scri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/board";
	}
	
	// 게시물 상세보기
	@RequestMapping(value="/list/{bno}", method=RequestMethod.GET)
	public String getBoard(@PathVariable("bno") int bno, SearchCriteria scri, Model model) throws Exception {
		logger.info("----------------list/" + bno + "----------------");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		model.addAttribute("pageMaker", pageMaker);
		
		model.addAttribute("board", service.read(bno));
		
		return "board/detail";
	}
	
	// 글 등록 페이지 이동
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registGET(BoardVO vo, Model model) throws Exception {
		logger.info("----------------regist get----------------");
		
		return "board/register";
	}
	
	// 글 등록 처리
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registPOST(BoardVO vo, RedirectAttributes ra) throws Exception {
		logger.info("----------------regist post----------------");
		logger.info(vo.toString());
		
		service.regist(vo);
		
		ra.addFlashAttribute("result", "InsertOK");
		
		return "redirect:/";
	}
	
	// 글 수정 페이지 이동
	@RequestMapping(value="/update/{bno}", method=RequestMethod.GET)
	public String updateGet(@PathVariable("bno") int bno, SearchCriteria scri, Model model) throws Exception {
		logger.info("----------------updateGET/" + bno + "----------------");
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		model.addAttribute("pageMaker", pageMaker);
		
		model.addAttribute("board", service.read(bno));
		
		return "board/update";
	}
	
	// 글 수정 처리
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updatePOST(BoardVO vo, SearchCriteria scri, RedirectAttributes ra) throws Exception {
		logger.info("----------------update----------------");
		
		service.modify(vo);
		
		ra.addAttribute("page", scri.getPage());
		ra.addAttribute("perPageNum", scri.getPerPageNum());
		ra.addAttribute("searchType", scri.getSearchType());
		ra.addAttribute("keyword", scri.getKeyword());
		ra.addFlashAttribute("result", "updateOK");
		
		return "redirect:/";
	}
	
	// 글 삭제 처리
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("bno") int bno, SearchCriteria scri, RedirectAttributes ra) throws Exception {
		logger.info("----------------delete/" + bno + "----------------");
		
		service.delete(bno);
		
		ra.addAttribute("page", scri.getPage());
		ra.addAttribute("perPageNum", scri.getPerPageNum());
		ra.addAttribute("searchType", scri.getSearchType());
		ra.addAttribute("keyword", scri.getKeyword());
		ra.addFlashAttribute("result", "DeleteOK");
		
		return "redirect:/";
	}
	
	
}
