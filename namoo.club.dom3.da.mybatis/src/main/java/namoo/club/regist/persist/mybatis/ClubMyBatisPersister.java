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
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.persist.mybatis.mapper.ClubMapper;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class ClubMyBatisPersister implements ClubPersister {
	//
	@Autowired
	private ClubMapper clubMapper;
	
	@Override
	public String create(Club club) {
			clubMapper.insertClub(club);
			return club.getId();
	}

	@Override
	public Club retrieve(String clubId) {
			return clubMapper.selectClub(clubId);
	}

	@Override
	public Club retrieveByName(String clubName) {
			return clubMapper.selectClubByName(clubName);
	}

	@Override
	public List<Club> retrieveAll() {
			return clubMapper.selectAllClubs();
	}

	@Override
	public void update(Club club) {
			clubMapper.updateClub(club);
	}

	@Override
	public void delete(Club club) {
			clubMapper.deleteClub(club.getId());
	}

	@Override
	public boolean isExistClubByName(String clubName) {
			Club club = clubMapper.selectClubByName(clubName);
			return club != null ? true : false;
	}
	
	//--------------------------------------------------------------------------

	@Override
	public void changeAdmin(String clubId, ClubAdmin admin) {
			Club club = clubMapper.selectClub(clubId);
			
			if (club == null) {
				return;
			}
			
			club.setAdmin(admin);
			clubMapper.updateClub(club);
	}

	@Override
	public ClubAdmin retreiveAdmin(String clubId) {
			Club club = clubMapper.selectClub(clubId);
			
			if (club == null) {
				return null;
			}
			return club.getAdmin();
	}

	@Override
	public List<Club> retrieveManagedClubs(String email) {
			return clubMapper.selectManagedClubs(email);
	}

	
}
