package org.spring.board;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class PasswordEncodigTest {
	
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	
	@Test
	public void passwordEncoding() {
		
		String pwEncode1 = pwEncoder.encode("test111");
		String pwEncode2 = pwEncoder.encode("test111");
		String pwEncode3 = pwEncoder.encode("test111");
		
		System.out.println("encoding1 --------> " + pwEncode1);
		System.out.println("encoding2 --------> " + pwEncode2);
		System.out.println("encoding3 --------> " + pwEncode3);
	}
}
