/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.lifecycle;

import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.TownerService;

public interface RegistServiceLifecycler {
	//
	public ClubService getClubService(); 
	public TownerService getTownerService(); 
}