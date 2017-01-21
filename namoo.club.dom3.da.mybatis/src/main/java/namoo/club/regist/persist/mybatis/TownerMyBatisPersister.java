/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.persist.mybatis.mapper.TownerMapper;

import org.apache.ibatis.session.SqlSession;

public class TownerMyBatisPersister implements TownerPersister {
	//
	
	@Override
	public String create(Towner towner) {
		// 
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			
			townerMapper.insertTowner(towner);
			return towner.getId();
			
		} finally {
			session.close();
		}
 	}

	@Override
	public Towner retrieve(String townerId) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			return townerMapper.selectTowner(townerId);
			
		} finally {
			session.close();
		}
	}

	@Override
	public Towner retrieveByEmail(String email) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			return townerMapper.selectTownerByEmail(email);
			
		} finally {
			session.close();
		}
	}

	@Override
	public void update(Towner towner) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			townerMapper.updateTowner(towner);
			
		} finally {
			session.close();
		}
	}

	@Override
	public void delete(Towner towner) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			townerMapper.deleteTowner(towner.getId());
			
		} finally {
			session.close();
		}
	}

	@Override
	public boolean isExistEmail(String email) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			TownerMapper townerMapper = session.getMapper(TownerMapper.class);
			
			Towner towner = townerMapper.selectTownerByEmail(email);
			return towner != null ? true : false;
			
		} finally {
			session.close();
		}
	}
}