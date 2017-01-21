/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.persist;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;

public interface ClubPersister {
	//
	// 1. Club operation 
	public String create(Club club);
	
	public Club retrieve(String clubId); 
	public Club retrieveByName(String clubName); 
	public List<Club> retrieveAll(); 

	public void update(Club club);
	public void delete(Club club);
	
	public boolean isExistClubByName(String clubName);
	
	// 2 Admin operation 
	public void changeAdmin(String clubId, ClubAdmin admin);
	
	public ClubAdmin retreiveAdmin(String clubId); 
	public List<Club> retrieveManagedClubs(String adminEmail); 
}