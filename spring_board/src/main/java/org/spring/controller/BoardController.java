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


/**
* 게시판에 대한 CRUD 클래스
* 
* @author L
*/
@Controller
@RequestMapping("/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	/**
	 * 글 목록을 보여주기 위한 함수
	 * 
	 * @param  scri 검색과 페이지 처리를 위한 파라미터
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String board(SearchCriteria scri, Model model) throws Exception {
		
		model.addAttribute("list", service.listCriteria(scri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.totalCount(scri));
		
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("title", "board");
		
		return "board/board";
	}
	
	/**
	 * 글 상세보기 위한 함수
	 * 
	 * @param  bno 해당 글을 가져오기 위한 글 번호 파라미터
	 * @param  scri 사용자가 검색 또는 어떤 페이지에서 왔는지 기억하기 위한 파라미터
	 */
	@RequestMapping(value="/list/{bno}", method=RequestMethod.GET)
	public String getBoard(@PathVariable("bno") int bno, SearchCriteria scri, Model model) throws Exception {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		model.addAttribute("pageMaker", pageMaker);
		
		model.addAttribute("board", service.read(bno));
		model.addAttribute("files", service.getFiles(bno));
		model.addAttribute("title", "detail");
		
		return "board/detail";
	}
	
	/**
	 * 글 등록페이지로 이동하기 위한 함수
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registGET(Model model) throws Exception {
		
		model.addAttribute("title", "register");
		
		return "board/register";
	}
	
	/**
	 * 글 등록을 위한 함수
	 * 
	 * @param  vo 글 등록을 위해 vo객체와 매핑시켜 주기 위한 파라미터
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registPOST(BoardVO vo, RedirectAttributes ra) throws Exception {
		
		service.regist(vo);
		
		ra.addFlashAttribute("result", "InsertOK");
		
		return "redirect:/";
	}
	
	/**
	 * 글 수정페이지로 이동하기 위한 함수
	 * 
	 * @param  bno 글 수정을 위해 현재 글번호를 가져와 페이지에 뿌려주기 위한 파라미터
	 * @param  scri 사용자가 검색 또는 어떤 페이지에서 왔는지 기억하기 위한 파라미터
	 */
	@RequestMapping(value="/update/{bno}", method=RequestMethod.GET)
	public String updateGet(@PathVariable("bno") int bno, SearchCriteria scri, Model model) throws Exception {
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		
		model.addAttribute("pageMaker", pageMaker);
		
		model.addAttribute("board", service.read(bno));
		
		model.addAttribute("title", "update");
		
		return "board/update";
	}
	
	/**
	 * 글 수정을 위한 함수
	 * 
	 * @param  vo 글 수정에 대한 정보를 vo객체에 담기 위한 파라미터
	 * @param  scri 사용자가 검색 또는 어떤 페이지에서 왔는지 기억하기 위한 파라미터
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updatePOST(BoardVO vo, SearchCriteria scri, RedirectAttributes ra) throws Exception {
	
		service.modify(vo);
		
		ra.addAttribute("page", scri.getPage());
		ra.addAttribute("perPageNum", scri.getPerPageNum());
		ra.addAttribute("searchType", scri.getSearchType());
		ra.addAttribute("keyword", scri.getKeyword());
		ra.addFlashAttribute("result", "updateOK");
		
		return "redirect:/";
	}
	
	/**
	 * 글 삭제를 위한 함수
	 * 
	 * @param  bno 해당 글을 삭제하기 위한 해당 글 번호 파라미터
	 * @param  scri 사용자가 검색 또는 어떤 페이지에서 왔는지 기억하기 위한 파라미터
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("bno") int bno, SearchCriteria scri, RedirectAttributes ra) throws Exception {
		
		service.delete(bno);
		
		ra.addAttribute("page", scri.getPage());
		ra.addAttribute("perPageNum", scri.getPerPageNum());
		ra.addAttribute("searchType", scri.getSearchType());
		ra.addAttribute("keyword", scri.getKeyword());
		ra.addFlashAttribute("result", "DeleteOK");
		
		return "redirect:/";
	}
		
}
