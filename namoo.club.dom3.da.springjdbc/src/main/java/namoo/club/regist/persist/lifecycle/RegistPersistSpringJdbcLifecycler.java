/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */
package namoo.club.regist.persist.lifecycle;

import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.domain.persist.TownerPersister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistPersistSpringJdbcLifecycler implements RegistPersistLifecycler {
	//
	@Autowired
	private TownerPersister townerPersister;
	
	@Autowired
	private ClubPersister clubPersister;
	
	@Autowired
	private ClubMemberPersister clubMemberPersister;
	
	//--------------------------------------------------------------------------
	
	public ClubPersister getClubPersister() {
		// 
		return clubPersister;
	}

	public ClubMemberPersister getClubMemberPersister() {
		// 
		return clubMemberPersister;
	}

	public TownerPersister getTownerPersister() {
		// 
		return townerPersister;
	}
}
