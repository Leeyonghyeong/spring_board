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

/**
 * security 로그인 처리 커스텀 클래스
 * 
 * @author L
 * 
 */
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	/** 
	 * DB의 값을 가져다주는 커스터마이징 객체 
	 */
	@Inject
	private UserDetailsService uds;
	
	/** 
	 * 비밀번호 암호화를 위한 객체 
	 */
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	/** 
	 * 로그인 인증 커스텀 함수
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		/* 사용자가 입력한 정보 */
		String userId = authentication.getName();
		String userPw = (String) authentication.getCredentials();
		
		/* DB에서 가져온 사용자정보 */
		UserDetailVO vo = (UserDetailVO) uds.loadUserByUsername(userId);
		
		/* 없는 사용자거나 ID 및 PW 가 틀릴 경우 예외 발생 */
		if(vo == null || !userId.equals(vo.getUserid()) || !pwEncoder.matches(userPw, vo.getPassword())) {
			throw new BadCredentialsException(userId);
		}
		
		vo.setPassword(null);
		
		/* 최종 리턴시킬 새로만든 Authentication 객체*/
		Authentication newAuth = new UsernamePasswordAuthenticationToken(vo, null, vo.getAuthorities());
		
		return newAuth;
	}
	
	/**
	 * 위에 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사를 위한 함수
	 * null 감이거나 잘못된 타입을 반환했을 경우 인증 실패
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		
		/* security가 요구하는 UsernamePasswordAuthenticationToken 타입이 맞는지 확인 */
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
