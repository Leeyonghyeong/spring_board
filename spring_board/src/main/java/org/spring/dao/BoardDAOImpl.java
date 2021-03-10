package org.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.spring.searching.SearchCriteria;
import org.spring.vo.BoardVO;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "boardMapper";
	
	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".create", vo);
	}
			
	@Override
	public BoardVO selectBoard(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".selectBoard", bno);
	}
	
	@Override
	public void update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".update", vo);
	}
	
	@Override
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".delete", bno);
	}
	
	@Override
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".listCriteria", scri);
	}
	
	@Override
	public int totalCount(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".totalCount", scri);
	}
}
