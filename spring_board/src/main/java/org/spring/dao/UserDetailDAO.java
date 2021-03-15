package org.spring.dao;

import org.spring.vo.UserDetailVO;

public interface UserDetailDAO {
	
	// 유저 정보 가져오기
	public UserDetailVO getUserById(String username) throws Exception;
	
	// 회원가입
	public void joinUser(UserDetailVO vo) throws Exception;
}
