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
import namoo.club.regist.domain.entity.ClubMember;

public interface ClubMemberPersister {
	//
	// Member operation 
	public void addMember(String clubId, ClubMember member);
	
	public ClubMember retrieve(String clubId, String memberEmail); 
	public List<ClubMember> retrieveAll(String clubId); 
	public List<Club> retrieveJoinedClub(String memberEmail); 
	public List<Club> retrieveUnjoinedClub(String memberEmail);
	
	public void delete(String clubId, String memberEmail);
	
	public boolean hasMember(String clubId, String email); 
	public int countMembers(String clubId); 
	public boolean hasJoinedClubs(String memberEmail); 
	public int countJoinedClubs(String memberEmail);
	
	
}