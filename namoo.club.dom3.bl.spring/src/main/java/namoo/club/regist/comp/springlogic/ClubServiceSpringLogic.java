/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */

package namoo.club.regist.comp.springlogic;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.regist.domain.service.logic.ClubServiceLogic;
import namoo.club.util.exception.NamooClubException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubServiceSpringLogic implements ClubService {
	//
	private ClubServiceLogic clubHouse; 
	
	@Autowired
	public ClubServiceSpringLogic(RegistPersistLifecycler persistLifecycler) {
		// 
		this.clubHouse = new ClubServiceLogic(persistLifecycler); 
	}

	@Override
	public String registerClub(ClubCDto clubCDto) throws NamooClubException {
		// 
		return clubHouse.registerClub(clubCDto); 
	}

	@Override
	public Club findClub(String clubId) throws NamooClubException {
		// 
		return clubHouse.findClub(clubId);
	}

	@Override
	public void joinAsMember(String clubId, String email) throws NamooClubException {
		// 
		clubHouse.joinAsMember(clubId, email); 
	}

	@Override
	public List<Club> findAllClubs() {
		// 
		return clubHouse.findAllClubs();
	}

	@Override
	public ClubMember findClubMember(String clubId, String email) throws NamooClubException {
		// 
		return clubHouse.findClubMember(clubId, email);
	}

	@Override
	public List<ClubMember> findAllClubMember(String clubId) throws NamooClubException {
		// 
		return clubHouse.findAllClubMember(clubId);
	}

	@Override
	public int countMembers(String clubId) {
		// 
		return clubHouse.countMembers(clubId);
	}

	@Override
	public void removeClub(String clubId) throws NamooClubException {
		// 
		clubHouse.removeClub(clubId);
	}

	@Override
	public List<Club> findJoinedClubs(String memberEmail) {
		// 
		return clubHouse.findJoinedClubs(memberEmail); 
	}

	@Override
	public List<Club> findUnjoinedClubs(String memberEmail) throws NamooClubException {
		// 
		return clubHouse.findUnjoinedClubs(memberEmail);
	}

	@Override
	public List<Club> findManagedClubs(String adminEmail) {
		// 
		return clubHouse.findManagedClubs(adminEmail);
	}

	@Override
	public void withdrawClub(String clubId, String memberEmail) throws NamooClubException {
		// 
		clubHouse.withdrawClub(clubId, memberEmail);
	}

	@Override
	public void modifyClub(Club club) {
		// 
		clubHouse.modifyClub(club); 
	}

	@Override
	public boolean hasClubMember(String clubId, String email) {
		// 
		return clubHouse.hasClubMember(clubId, email);
	}
}
