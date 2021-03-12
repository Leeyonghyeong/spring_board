package org.spring.dao;

import org.spring.vo.UserDetailVO;

public interface UserDetailDAO {
	
	public UserDetailVO getUserById(String username) throws Exception;
}
