package org.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.spring.dao.BoardDAO;
import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;
	
	@Override
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return dao.listCriteria(scri);
	}
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED)
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.updateViewCnt(bno);
		return dao.selectBoard(bno);
	}
	
	@Override
	@Transactional
	public void regist(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.create(vo);
		
		String[] files = vo.getFiles();
		
		if(files == null) { return; }
		
		for(String filename : files) {
			dao.addFiles(filename);
		}
	}
	
	@Override
	@Transactional
	public void modify(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.update(vo);
		
		Integer bno = vo.getBno();
		
		dao.deleteFiles(bno);
		
		String[] files = vo.getFiles();
		
		if(files == null) { return; }
		
		for(String filename : files) {
			dao.modifyFiles(bno, filename);
		}
	}
	
	@Override
	@Transactional
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteFiles(bno);
		dao.delete(bno);
	}
	
	@Override
	public int totalCount(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return dao.totalCount(scri);
	}
	
	@Override
	public List<String> getFiles(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.getFiles(bno);
	}
}
