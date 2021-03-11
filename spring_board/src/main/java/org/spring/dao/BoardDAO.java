package org.spring.dao;

import java.util.List;

import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;

public interface BoardDAO {
	
	// 모든 글 보기 및 검색 처리
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception;

	// 글 상세 보기
	public BoardVO selectBoard(Integer bno) throws Exception;
	
	// 글 등록
	public void create(BoardVO vo) throws Exception;
	
	// 글 수정
	public void update(BoardVO vo) throws Exception;
	
	// 글 삭제
	public void delete(Integer bno) throws Exception;
	
	// 모든 글 갯수 및 검색조건에 맞는 글 갯수 가져오기
	public int totalCount(SearchCriteria scri) throws Exception;
		
	// 댓글 수 업데이트
	public void updateReplyCnt(Integer bno, int amount) throws Exception;
	
	// 조회 수 업데이트
	public void updateViewCnt(Integer bno) throws Exception;
	
	// 첨부파일 등록
	public void addFiles(String filename) throws Exception;
	
	// 첨부파일 가져오기
	public List<String> getFiles(Integer bno) throws Exception;
	
	// 첨부파일 수정
	public void modifyFiles(Integer bno, String filename) throws Exception;
	
	// 첨부파일 삭제
	public void deleteFiles(Integer bno) throws Exception;
}
