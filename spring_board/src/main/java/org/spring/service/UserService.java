package org.spring.service;

import org.spring.vo.UserDetailVO;

public interface UserService {
	
	public UserDetailVO getUserById(String username) throws Exception;
	
	public void joinUser(UserDetailVO vo) throws Exception;
}
