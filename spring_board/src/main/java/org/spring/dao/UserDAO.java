package org.spring.dao;

import org.spring.vo.UserVO;

public interface UserDAO {
	public String getTime();
	
	public void insertUser(UserVO vo);
	
	public UserVO selectUser(String userid) throws Exception;
	
	public UserVO readWithPW(String userid, String password) throws Exception;
}
