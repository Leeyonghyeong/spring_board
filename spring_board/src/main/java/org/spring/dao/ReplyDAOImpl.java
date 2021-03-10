package org.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.spring.paging.Criteria;
import org.spring.vo.ReplyVO;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "replyMapper";
	
	@Override
	public List<ReplyVO> list(Integer bno, Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("bno", bno);
		paramMap.put("cri", cri);
		
		return session.selectList(namespace + ".list", paramMap);
	}
	
	@Override
	public void register(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".register", vo);
	}
	
	@Override
	public void update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".update", vo);
	}
	
	@Override
	public void delete(Integer rno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace + ".delete", rno);
	}
	
	@Override
	public int count(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".count", bno);
	}
}
