package org.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.spring.dao.BoardDAO;
import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;
	
	@Override
	public void regist(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.create(vo);
	}
	
	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectBoard(bno);
	}
	
	@Override
	public void modify(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
	}
	
	@Override
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bno);
	}
	
	@Override
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(scri);
	}
	
	@Override
	public int totalCount(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return dao.totalCount(scri);
	}
}
