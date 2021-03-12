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
		System.out.println("username-----------> " + username);
		return session.selectOne(namespace + ".getUser", username);
	}
}
