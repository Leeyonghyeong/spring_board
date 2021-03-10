package org.spring.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.spring.vo.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace = "userMapper";
	
	@Override
	public String getTime() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".getTime");
	}
	
	@Override
	public void insertUser(UserVO vo) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace+".insertMember", vo);
	}
	
	@Override
	public UserVO selectUser(String userid) throws Exception {
		// TODO Auto-generated method stub
		return (UserVO)sqlSession.selectOne(namespace +".selectUser", userid);
	}
	
	@Override
	public UserVO readWithPW(String userid, String password) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("userid", userid);
		paramMap.put("password", password);		
		
		return (UserVO)sqlSession.selectOne(namespace +".selectUser", paramMap);
	}
	
	
}
