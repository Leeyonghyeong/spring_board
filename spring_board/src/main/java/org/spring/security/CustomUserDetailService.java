package org.spring.security;

import javax.inject.Inject;

import org.spring.dao.UserDetailDAO;
import org.spring.vo.UserDetailVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService{
	
	@Inject
	private UserDetailDAO udDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetailVO user = null;
		
		try {
			user = udDAO.getUserById(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	

}
