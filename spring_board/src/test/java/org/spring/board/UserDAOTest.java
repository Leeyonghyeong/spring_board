package org.spring.board;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spring.dao.UserDAO;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class UserDAOTest {
	
	@Inject
	private UserDAO dao;
	
	@Test
	public void testTime() throws Exception {
		System.out.println(dao.getTime());
	}
}
