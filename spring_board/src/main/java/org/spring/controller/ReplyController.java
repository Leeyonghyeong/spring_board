package org.spring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.spring.paging.Criteria;
import org.spring.paging.PageMaker;
import org.spring.service.ReplyService;
import org.spring.vo.ReplyVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
* 댓글에 대한 CRUD 클래스
* 
* @author L
*/
@RestController
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Inject
	private ReplyService service;
	
	/**
	 * 해당하는 글에 대한 댓글을 보여주기 위한 함수
	 * 
	 * @param  bno 해당 글 번호 파라미터
	 * @param  page 댓글 목록 페이징 파라미터
	 */
	@RequestMapping(value="/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> list(@PathVariable("bno") Integer bno, @PathVariable("page") Integer page) {
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			List<ReplyVO> list = service.list(bno, cri);
			
			map.put("list", list);
			
			int count = service.count(bno);
			pageMaker.setTotalCount(count);
			
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/**
	 * 댓글 등록을 위한 함수
	 * 
	 * @param  vo 댓글 vo객체에 정보를 저장하기 위한 파라미터
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		
		ResponseEntity<String> entity = null;
		
		try {
			service.register(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/**
	 * 댓글 수정을 위한 함수
	 * 
	 * @param  rno 해당 댓글 번호 파라미터
	 * @param  vo 댓글 vo객체에 정보를 저장하기 위한 파라미터
	 */
	@RequestMapping(value="/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno, @RequestBody ReplyVO vo) {
		
		ResponseEntity<String> entity = null;
		
		try {
			service.update(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/**
	 * 댓글 삭제를 위한 함수
	 * 
	 * @param  rno 해당 댓글 번호 파라미터
	 * @param  bno 댓글이 달린 게시물 번호 파라미터
	 */
	@RequestMapping(value="/{rno}/{bno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno, @PathVariable("bno") Integer bno) {
		
		ResponseEntity<String> entity = null;
		
		try {
			service.delete(rno, bno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
}
