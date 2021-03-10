package org.spring.service;

import java.util.List;

import org.spring.paging.Criteria;
import org.spring.vo.ReplyVO;

public interface ReplyService {
	
	public List<ReplyVO> list(Integer bno, Criteria cri) throws Exception;
	
	public void register(ReplyVO vo) throws Exception;
	
	public void update(ReplyVO vo) throws Exception;
	
	public void delete(Integer rno, Integer bno) throws Exception;
	
	public int count(Integer bno) throws Exception;
}
