package org.spring.dao;

import java.util.List;

import org.spring.paging.Criteria;
import org.spring.vo.ReplyVO;

public interface ReplyDAO {
	
	// 댓글 리스트
	public List<ReplyVO> list(Integer bno, Criteria cri) throws Exception;
	
	// 댓글 등록
	public void register(ReplyVO vo) throws Exception;

	// 댓글 수정
	public void update(ReplyVO vo) throws Exception;
	
	// 댓글 삭제
	public void delete(Integer rno) throws Exception;
	
	// 해당 글에 달린 댓글의 수 가져오기
	public int count(Integer bno) throws Exception;
	
}
