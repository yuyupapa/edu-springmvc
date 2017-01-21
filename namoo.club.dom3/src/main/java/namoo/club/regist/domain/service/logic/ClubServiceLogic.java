/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.service.logic;
import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.util.exception.EmptyResultException;
import namoo.club.util.exception.NamooClubException;

public class ClubServiceLogic implements ClubService {
	//
	private ClubPersister clubPersister;
	private ClubMemberPersister clubMemberPersister; 
	private TownerPersister townerPersister;

	//--------------------------------------------------------------------------
	public ClubServiceLogic(RegistPersistLifecycler lifecycler) {
		// 
		this.clubPersister = lifecycler.getClubPersister(); 
		this.clubMemberPersister = lifecycler.getClubMemberPersister(); 
		this.townerPersister = lifecycler.getTownerPersister(); 
	}

	//--------------------------------------------------------------------------
	@Override
	public String registerClub(ClubCDto clubCDto) throws NamooClubException {
		//
		if (clubPersister.isExistClubByName(clubCDto.getClubName()))  {
			throw new NamooClubException("Already existed club --> " + clubCDto.getClubName());
		}
		
		Towner towner = null;
		try {
			towner = townerPersister.retrieveByEmail(clubCDto.getAdminEmail());
		}
		catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
		 
		
		Club club = clubCDto.createDomain(new ClubAdmin(towner)); 
		clubPersister.create(club);
		ClubMember clubMember = new ClubMember(club, towner); 
		clubMemberPersister.addMember(club.getId(), clubMember); 
		
		return club.getId();
	}

	@Override
	public Club findClub(String clubId) throws NamooClubException {
		//
		try {
			return clubPersister.retrieve(clubId);
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("Club Id --> " + clubId);
		}
	}

	@Override
	public void joinAsMember(String clubId, String email) throws NamooClubException {
		// 
		Club club = null;
		Towner towner = null;
		try {
			club = clubPersister.retrieve(clubId);
			towner = townerPersister.retrieveByEmail(email); 
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("Club Id --> " + clubId + ", Towner email --> " + email);
		}
		
		
		if (clubMemberPersister.hasMember(clubId, email)) {
			return; 
		}
		
		ClubMember member = new ClubMember(club, towner); 
		clubMemberPersister.addMember(clubId, member);
	}

	@Override
	public ClubMember findClubMember(String clubId, String email) throws NamooClubException {
		// 
		try {
			return clubMemberPersister.retrieve(clubId, email); 
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("Club Id --> " + clubId + ", Towner email --> " + email);
		}
	}

	@Override
	public List<ClubMember> findAllClubMember(String clubId) throws NamooClubException {
		// 
		try {
			return clubMemberPersister.retrieveAll(clubId);
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("Club Id --> " + clubId);
		}
	}

	@Override
	public int countMembers(String clubId){
		//
		return clubMemberPersister.countMembers(clubId); 
	}

	@Override
	public void removeClub(String clubId) throws NamooClubException {
		// 
		try {
			Club club = clubPersister.retrieve(clubId); 
			clubPersister.delete(club);
		}
		catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
	}

	@Override
	public List<Club> findAllClubs() {
		// 
		return clubPersister.retrieveAll(); 
	}

	@Override
	public List<Club> findJoinedClubs(String memberEmail) {
		// 
		return clubMemberPersister.retrieveJoinedClub(memberEmail);
	}
	
	@Override
	public List<Club> findUnjoinedClubs(String memberEmail) throws NamooClubException {
		// 
		if (!townerPersister.isExistEmail(memberEmail)) {
			throw new NamooClubException(memberEmail + " is not existing");
		}
		
		return clubMemberPersister.retrieveUnjoinedClub(memberEmail); 
	}

	@Override
	public List<Club> findManagedClubs(String adminEmail) {
		// 
		return clubPersister.retrieveManagedClubs(adminEmail); 
	}

	@Override
	public void withdrawClub(String clubId, String memberEmail) throws NamooClubException {
		//
		try {
			Club club = clubPersister.retrieve(clubId);
			
			// 클럽 관리자는 탈퇴할 수 없다
			if (club.getAdmin().getEmail().equals(memberEmail)) {
				throw new NamooClubException("Admin can't withdraw.");
			}
		} catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
		clubMemberPersister.delete(clubId, memberEmail); 
	}

	@Override
	public void modifyClub(Club club) {
		// 
		clubPersister.update(club);
	}

	@Override
	public boolean hasClubMember(String clubId, String email) {
		// 
		return clubMemberPersister.hasMember(clubId, email);
	}
}