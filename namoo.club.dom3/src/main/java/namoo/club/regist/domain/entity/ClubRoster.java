/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.entity;

import java.util.ArrayList;
import java.util.List;

import namoo.club.util.identity.Identifiable;

public class ClubRoster implements Identifiable {
	//
	private String id; 
	private List<ClubMember> members;
	
	//--------------------------------------------------------------------------
	private ClubRoster() {
		//
		this.members = new ArrayList<ClubMember>(); 
	}

	public ClubRoster(Club club) {
		// 
		this(); 
		this.id = club.getId(); 
	}
	
	//--------------------------------------------------------------------------
	@Override
	public String getId() {
		return id; 
	}

	public void addMember(ClubMember member) {
		members.add(member); 
	}
	
	public List<ClubMember> getMembers() {
		return members;
	}
}
