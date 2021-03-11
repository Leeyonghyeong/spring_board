package org.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<BoardVO> listCriteria(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".listCriteria", scri);
	}
			
	@Override
	public BoardVO selectBoard(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".selectBoard", bno);
	}
	
	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".create", vo);
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
	public int totalCount(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".totalCount", scri);
	}
	
	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		// TODO Auto-generated method stub		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("bno", bno);
		map.put("amount", amount);
		
		session.update(namespace + ".updateReplyCnt", map);
	}
	
	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".updateViewCnt", bno);
	}
	
	@Override
	public void addFiles(String filename) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".addFiles", filename);
	}
	
	@Override
	public List<String> getFiles(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".getFiles", bno);
	}
	
	@Override
	public void modifyFiles(Integer bno, String filename) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("bno", bno);
		map.put("filename", filename);
		
		session.insert(namespace + ".modifyFiles", map);
	}
	
	@Override
	public void deleteFiles(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".deleteFiles", bno);
	}
}
