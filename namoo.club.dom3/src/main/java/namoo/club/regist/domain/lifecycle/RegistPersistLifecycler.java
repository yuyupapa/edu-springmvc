/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.lifecycle;

import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.domain.persist.TownerPersister;

public interface RegistPersistLifecycler {
	//
	public ClubPersister getClubPersister(); 
	public ClubMemberPersister getClubMemberPersister(); 
	public TownerPersister getTownerPersister(); 
}