/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis.mapper;

import java.util.List;

import namoo.club.regist.domain.entity.Club;


public interface ClubMapper {
	//
	public void insertClub(Club club);
	
	public Club selectClub(String clubId);
	public Club selectClubByName(String clubName);
	public List<Club> selectAllClubs();
	public List<Club> selectManagedClubs(String email);
	
	public void updateClub(Club club);
	public void deleteClub(String clubId);
}
