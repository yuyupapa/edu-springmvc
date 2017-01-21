/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.comp.pojologic;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.regist.domain.service.logic.ClubServiceLogic;
import namoo.club.util.exception.NamooClubException;

public class ClubServicePojoLogic implements ClubService {
	//
	private ClubServiceLogic clubService; 
	
	public ClubServicePojoLogic(RegistPersistLifecycler persistLifecycler) {
		// 
		this.clubService = new ClubServiceLogic(persistLifecycler); 
	}

	@Override
	public String registerClub(ClubCDto clubCDto) throws NamooClubException {
		// 
		return clubService.registerClub(clubCDto); 
	}

	@Override
	public Club findClub(String clubId) throws NamooClubException {
		// 
		return clubService.findClub(clubId);
	}

	@Override
	public void joinAsMember(String clubId, String email) throws NamooClubException {
		// 
		clubService.joinAsMember(clubId, email); 
	}

	@Override
	public List<Club> findAllClubs() {
		// 
		return clubService.findAllClubs();
	}

	@Override
	public ClubMember findClubMember(String clubId, String email) throws NamooClubException {
		// 
		return clubService.findClubMember(clubId, email);
	}

	@Override
	public List<ClubMember> findAllClubMember(String clubId) throws NamooClubException {
		// 
		return clubService.findAllClubMember(clubId);
	}

	@Override
	public int countMembers(String clubId) {
		// 
		return clubService.countMembers(clubId);
	}

	@Override
	public void removeClub(String clubId) throws NamooClubException {
		// 
		clubService.removeClub(clubId);
	}

	@Override
	public List<Club> findJoinedClubs(String memberEmail) {
		// 
		return clubService.findJoinedClubs(memberEmail); 
	}

	@Override
	public List<Club> findUnjoinedClubs(String memberEmail) throws NamooClubException {
		// 
		return clubService.findUnjoinedClubs(memberEmail);
	}

	@Override
	public List<Club> findManagedClubs(String adminEmail) {
		// 
		return clubService.findManagedClubs(adminEmail);
	}

	@Override
	public void withdrawClub(String clubId, String memberEmail) throws NamooClubException {
		// 
		clubService.withdrawClub(clubId, memberEmail);
	}

	@Override
	public void modifyClub(Club club) {
		// 
		clubService.modifyClub(club); 
	}
	
	@Override
	public boolean hasClubMember(String clubId, String email) {
		// 
		return clubService.hasClubMember(clubId, email);
	}
}
