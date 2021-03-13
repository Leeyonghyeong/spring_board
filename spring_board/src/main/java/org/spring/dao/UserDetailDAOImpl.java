package org.spring.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.spring.vo.UserDetailVO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailDAOImpl implements UserDetailDAO{
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "userMapper";
	
	@Override
	public UserDetailVO getUserById(String username) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".getUser", username);
	}
	
	@Override
	public void joinUser(UserDetailVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".joinUser", vo);
	}
}
