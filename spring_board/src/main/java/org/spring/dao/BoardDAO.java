package org.spring.dao;

import java.util.List;

import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;

public interface BoardDAO {
	
	// 글 등록
	public void create(BoardVO vo) throws Exception;
	
	// 글 상세 보기
	public BoardVO selectBoard(Integer bno) throws Exception;
	
	// 모든 글 보기 및 검색 처리
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception;
	
	// 글 수정
	public void update(BoardVO vo) throws Exception;
	
	// 글 삭제
	public void delete(Integer bno) throws Exception;
	
	// 모든 글 갯수 및 검색조건에 맞는 글 갯수 가져오기
	public int totalCount(SearchCriteria scri) throws Exception;
	
}
