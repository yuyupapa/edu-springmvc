/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */

package namoo.club.regist.comp.springlogic;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.service.TownerService;
import namoo.club.regist.domain.service.dto.TownerCDto;
import namoo.club.regist.domain.service.logic.TownerServiceLogic;
import namoo.club.util.exception.NamooClubException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TownerServiceSpringLogic implements TownerService {
	//
	private TownerServiceLogic townerService; 
	
	@Autowired
	public TownerServiceSpringLogic(RegistPersistLifecycler persistLifecycler) {
		// 
		this.townerService = new TownerServiceLogic(persistLifecycler); 
	}
	
	@Override
	public String registerTowner(TownerCDto townerCDto) throws NamooClubException {
		// 
		return townerService.registerTowner(townerCDto); 
	}

	@Override
	public Towner findTowner(String townerId) throws NamooClubException {
		// 
		return townerService.findTowner(townerId);
 	}
	
	@Override
	public Towner findTownerByEmail(String email) throws NamooClubException {
		// 
		return townerService.findTownerByEmail(email);
	}

	@Override
	public void modifyTowner(Towner towner) {
		// 
		townerService.modifyTowner(towner);
	}

	@Override
	public void removeTowner(String townerId) throws NamooClubException {
		// 
		townerService.removeTowner(townerId);
	}

	@Override
	public void removeTownerByEmail(String email) throws NamooClubException {
		// 
		townerService.removeTownerByEmail(email);
	}

	@Override
	public boolean loginAsTowner(String email, String password) throws NamooClubException {
		// 
		return townerService.loginAsTowner(email, password);
	}
}
