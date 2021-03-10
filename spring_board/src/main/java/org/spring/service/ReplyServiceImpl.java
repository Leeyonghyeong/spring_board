package org.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.spring.dao.ReplyDAO;
import org.spring.paging.Criteria;
import org.spring.vo.ReplyVO;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> list(Integer bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return dao.list(bno, cri);
	}

	@Override
	public void register(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.register(vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
	}

	@Override
	public void delete(Integer rno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(rno);
	}
	
	@Override
	public int count(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.count(bno);
	}

}
