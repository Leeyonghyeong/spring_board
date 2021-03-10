package org.spring.service;

import java.util.List;

import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;

public interface BoardService {
	
	// 글 등록
	public void regist(BoardVO vo) throws Exception;
	
	// 글 상세보기
	public BoardVO read(Integer bno) throws Exception;
	
	// 글 수정
	public void modify(BoardVO vo) throws Exception;
	
	// 글 삭제
	public void delete(Integer bno) throws Exception;
	
	// 모든 글 및 검색 조건에 맞는 글 리스트
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception;
	
	// 모든 글 갯수 및 검색 조건에 맞는 글 갯수
	public int totalCount(SearchCriteria scri) throws Exception;

}
