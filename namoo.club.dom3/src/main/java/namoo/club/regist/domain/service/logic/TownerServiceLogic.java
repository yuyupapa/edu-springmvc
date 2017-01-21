/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.service.logic;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.domain.service.TownerService;
import namoo.club.regist.domain.service.dto.TownerCDto;
import namoo.club.util.exception.EmptyResultException;
import namoo.club.util.exception.NamooClubException;

public class TownerServiceLogic implements TownerService {
	//
	private ClubMemberPersister clubMemberPersister; 
	private TownerPersister townerPersister;

	public TownerServiceLogic(RegistPersistLifecycler lifecycler) {
		// 
		this.clubMemberPersister = lifecycler.getClubMemberPersister(); 
		this.townerPersister = lifecycler.getTownerPersister(); 
	}
	
	//--------------------------------------------------------------------------
	@Override
	public boolean loginAsTowner(String email, String password) throws NamooClubException {
		// 
		Towner towner = null; 
		try {
			towner = townerPersister.retrieveByEmail(email);
		}
		catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
		
		if (towner == null) {
			return false; 
		}
		
		if (!towner.getPassword().equals(password)) {
			return false; 
		}
		
		return true;
	}

	@Override
	public String registerTowner(TownerCDto townerCDto) throws NamooClubException {
		// 
		if (townerPersister.isExistEmail(townerCDto.getEmail())) { 
			throw new NamooClubException("Email is already exist. --> " + townerCDto.getEmail());
		}
		
		Towner towner = townerCDto.createDomain(); 
		townerPersister.create(towner);
		
		return towner.getId(); 
	}

	@Override
	public void removeTowner(String townerId) throws NamooClubException {
		// 
		Towner towner = null;
		try {
			towner = townerPersister.retrieve(townerId);
		} catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
		
		String email = towner.getEmail();
		
		// 커뮤니티의 회원으로 가입된 경우 삭제하지 못한다.
		if (clubMemberPersister.hasJoinedClubs(email)) {
			int count = clubMemberPersister.countJoinedClubs(email); 
			throw new NamooClubException("Please withdrawal from [" + count + "] clubs");
		}
		
		townerPersister.delete(towner);
	}
	
	@Override
	public void removeTownerByEmail(String email) throws NamooClubException {
		// 
		// 커뮤니티의 회원으로 가입된 경우 삭제하지 못한다.
		if (clubMemberPersister.hasJoinedClubs(email)) {
			int count = clubMemberPersister.countJoinedClubs(email); 
			throw new NamooClubException("Please withdrawal from [" + count + "] clubs");
		}
		
		Towner towner = null;
		try {
			towner = townerPersister.retrieveByEmail(email); 
		}
		catch (EmptyResultException e) {
			throw new NamooClubException(e);
		}
		
		if (towner == null) {
			return; 
		}
		
		townerPersister.delete(towner);
	}

	@Override
	public Towner findTowner(String townerId) throws NamooClubException {
		//
		try {
			return townerPersister.retrieve(townerId);
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("No such towner by id --> " + townerId);
		}
	} 
	
	@Override
	public Towner findTownerByEmail(String email) throws NamooClubException {
		//
		try {
			return townerPersister.retrieveByEmail(email);
		}
		catch (EmptyResultException e) {
			throw new EmptyResultException("No such towner by email --> " + email);
		}
	} 

	@Override
	public void modifyTowner(Towner towner) {
		// 
		townerPersister.update(towner);
	}
}
