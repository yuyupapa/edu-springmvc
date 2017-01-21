/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.tools.RunScript;

public class MyBatisSessionFactory {
	//
	private static SqlSessionFactory sqlSessionFactory;
	
	// -------------------------------------------------------------------------
	
	private MyBatisSessionFactory() {
	}
	
	// -------------------------------------------------------------------------
	
	public static void initDatabase() throws SQLException {
		//
		Connection conn = getSession().getConnection();
		
		InputStream is = MyBatisSessionFactory.class.getResourceAsStream("/schema.ddl");
		Reader reader = new InputStreamReader(is);
		
		RunScript.execute(conn, reader);
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		//
		try {
			String resource = "/namoo/club/comp/da/mybatis/mybatis-config.xml";
			InputStream is = MyBatisSessionFactory.class.getResourceAsStream(resource);
			Reader reader = new InputStreamReader(is);
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}
	
	public static void setSqlSessionFactory(Reader resourceReader) {
		//
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
	}
	
	public static SqlSession getSession() {
		//
		if (sqlSessionFactory == null) {
			return getSqlSessionFactory().openSession(true);
		}
		else {
			return sqlSessionFactory.openSession(true);
		}
		
	}
	
}
