/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.lifecycle;

import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.persist.mybatis.ClubMemberMyBatisPersister;
import namoo.club.regist.persist.mybatis.ClubMyBatisPersister;
import namoo.club.regist.persist.mybatis.TownerMyBatisPersister;

public class RegistPersistMyBatisLifecycler implements RegistPersistLifecycler {
	//
	private static RegistPersistMyBatisLifecycler lifecycler = new RegistPersistMyBatisLifecycler();
	
	// -------------------------------------------------------------------------
	private RegistPersistMyBatisLifecycler() {
		//
	}
	
	@Override
	public ClubPersister getClubPersister() {
		//
		return new ClubMyBatisPersister();
	}

	@Override
	public ClubMemberPersister getClubMemberPersister() {
		//
		return new ClubMemberMyBatisPersister();
	}

	@Override
	public TownerPersister getTownerPersister() {
		//
		return new TownerMyBatisPersister();
	}
	
	public static RegistPersistLifecycler getInstance() {
		//
		if(lifecycler == null) {
			lifecycler = new RegistPersistMyBatisLifecycler();
		}
		
		return lifecycler;
	}
}
