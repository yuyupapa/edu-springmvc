/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.persist.mybatis.mapper.ClubMemberMapper;

import org.apache.ibatis.session.SqlSession;

public class ClubMemberMyBatisPersister implements ClubMemberPersister {
	//
	
	@Override
	public void addMember(String clubId, ClubMember member) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			clubMemberMapper.insertClubMember(clubId, member);
		} finally {
			session.close();
		}
	}
	
	@Override
	public ClubMember retrieve(String clubId, String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			return clubMemberMapper.selectClubMember(clubId, memberEmail);
		} finally {
			session.close();
		}
	}

	@Override
	public List<ClubMember> retrieveAll(String clubId) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			return clubMemberMapper.selectAllClubMember(clubId);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Club> retrieveJoinedClub(String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			return clubMemberMapper.selectJoinedClub(memberEmail);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Club> retrieveUnjoinedClub(String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			return clubMemberMapper.selectUnjoinedClub(memberEmail);
		} finally {
			session.close();
		}
	}
	
	@Override
	public void delete(String clubId, String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			clubMemberMapper.deleteClubMember(clubId, memberEmail);
		} finally {
			session.close();
		}
	}

	@Override
	public boolean hasMember(String clubId, String email) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			ClubMember clubMember = clubMemberMapper.selectClubMember(clubId, email);
			return clubMember != null ? true : false;
		} finally {
			session.close();
		}
	}

	@Override
	public int countMembers(String clubId) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			return clubMemberMapper.countMembers(clubId);
		} finally {
			session.close();
		}
	}

	@Override
	public boolean hasJoinedClubs(String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			List<Club> joinedClub = clubMemberMapper.selectJoinedClub(memberEmail);
			return (joinedClub == null) || (joinedClub.isEmpty()) ? false : true;
		} finally {
			session.close();
		}
	}

	@Override
	public int countJoinedClubs(String memberEmail) {
		//
		SqlSession session = MyBatisSessionFactory.getSession();
		
		try {
			ClubMemberMapper clubMemberMapper = session.getMapper(ClubMemberMapper.class);
			
			List<Club> joinedClub = clubMemberMapper.selectJoinedClub(memberEmail);
			return joinedClub.size();
		} finally {
			session.close();
		}
	}
	
}