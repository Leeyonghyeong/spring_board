package org.spring.security;

import javax.inject.Inject;

import org.spring.vo.UserDetailVO;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Inject
	private UserDetailsService uds;
	
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		String userId = authentication.getName();
		String userPw = (String) authentication.getCredentials();
		
		UserDetailVO vo = (UserDetailVO) uds.loadUserByUsername(userId);
		
		if(vo == null || !userId.equals(vo.getUserid()) || !pwEncoder.matches(userPw, vo.getPassword())) {
			throw new BadCredentialsException(userId);
		}
		
		vo.setPassword(null);
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(vo, null, vo.getAuthorities());
		
		return newAuth;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
