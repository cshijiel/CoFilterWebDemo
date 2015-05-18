package com.roc.springmvc;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.roc.springmvc.beans.User;
import com.roc.springmvc.dao.CoFilterRepository;
import com.roc.springmvc.dao.MyBatisConnectionFactory;

public class MyBatisFactoryTest {
	
	
	@Test
	public void testDB() throws IOException{
		SqlSessionFactory sq = MyBatisConnectionFactory.getSqlSessionFactory();
		SqlSession  session = sq.openSession();
		
		CoFilterRepository coFilterRepository = session.getMapper(CoFilterRepository.class);
		
		User user = coFilterRepository.getUser(26);
		System.out.println(user);
	}

}
