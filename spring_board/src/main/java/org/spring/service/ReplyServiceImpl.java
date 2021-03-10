package org.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.spring.dao.BoardDAO;
import org.spring.dao.ReplyDAO;
import org.spring.paging.Criteria;
import org.spring.vo.ReplyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO rdao;
	
	@Inject
	private BoardDAO bdao;
	
	@Override
	public List<ReplyVO> list(Integer bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return rdao.list(bno, cri);
	}
	
	@Override
	@Transactional
	public void register(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		rdao.register(vo);
		bdao.updateReplyCnt(vo.getBno(), 1);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		rdao.update(vo);
	}

	@Override
	@Transactional
	public void delete(Integer rno, Integer bno) throws Exception {
		// TODO Auto-generated method stub
		rdao.delete(rno);
		bdao.updateReplyCnt(bno, -1);
	}
	
	@Override
	public int count(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return rdao.count(bno);
	}

}
