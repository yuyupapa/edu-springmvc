/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.comp.lifecycle;

import namoo.club.regist.comp.pojologic.ClubServicePojoLogic;
import namoo.club.regist.comp.pojologic.TownerServicePojoLogic;
import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.lifecycle.RegistServiceLifecycler;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.TownerService;

public class RegistServicePojoLifecycler implements RegistServiceLifecycler {
	//
	private static RegistServicePojoLifecycler serviceLifecycler; 
	private RegistPersistLifecycler persistLifecycler; 
	
	private RegistServicePojoLifecycler(RegistPersistLifecycler persistLifecycler) {
		// 
		this.persistLifecycler = persistLifecycler; 
	}
	
	public static RegistServiceLifecycler getInstance(RegistPersistLifecycler persistLifecycler) {
		//
		if(serviceLifecycler == null) {
			serviceLifecycler = new RegistServicePojoLifecycler(persistLifecycler);
		}
		
		return serviceLifecycler; 
	}
	
	@Override
	public ClubService getClubService() {
		// 
		return new ClubServicePojoLogic(persistLifecycler);
	}

	@Override
	public TownerService getTownerService() {
		// 
		return new TownerServicePojoLogic(persistLifecycler);
	}
}
