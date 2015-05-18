/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：Roc
 * 联系方式：chenpeng03@gyyx.cn 
 * 创建时间： 2014年11月17日 下午5:42:13
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 单例模式生成sqlSessionFactory
-------------------------------------------------------------------------*/
package com.roc.springmvc.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * MyBatis连接工厂
 * 
 * @author Chen
 *
 */
public class MyBatisConnectionFactory {
	private static SqlSessionFactory sqlSessionFactory = null;

	/**
	 * 单例模式生成sqlSessionFactory
	 * 
	 * @return
	 * @throws IOException
	 */
	public static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			synchronized (MyBatisConnectionFactory.class) {
				if (sqlSessionFactory == null) {
					String resource = "mybatis-config.xml";
					InputStream inputStream = null;
					try {
						inputStream = Resources.getResourceAsStream(resource);
					} catch (IOException e) {
						e.printStackTrace();
					}
					sqlSessionFactory = new SqlSessionFactoryBuilder()
							.build(inputStream);
				}
			}
		}
		return sqlSessionFactory;
	}

	private MyBatisConnectionFactory() {

	}
}
