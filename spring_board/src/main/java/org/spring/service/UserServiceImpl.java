package org.spring.service;

import javax.inject.Inject;

import org.spring.dao.UserDetailDAO;
import org.spring.vo.UserDetailVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	@Inject
	private UserDetailDAO dao;
	
	@Override
	public UserDetailVO getUserById(String username) throws Exception {
		// TODO Auto-generated method stub
		return dao.getUserById(username);
	}
	
	@Override
	public void joinUser(UserDetailVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.joinUser(vo);
	}
}
