/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.entity;

import java.util.Date;

import namoo.club.util.identity.Identifiable;

public class Club implements Identifiable {
	//
	private String id;
	private String name;
	private String description;
	private Date openDate;
	
	private ClubAdmin admin; 
	private ClubRoster roster; 

	//--------------------------------------------------------------------------
	private Club() {
		// 
		this.openDate = new Date();
		this.roster = new ClubRoster(this); 
	}
	
	public Club(String id) {
		//
		this();
		this.id = id;
	}
	
	public Club(String clubName, String description, ClubAdmin admin){
		//
		this();
		this.name = clubName; 
		this.description = description; 
		this.admin = admin; 
	}
	
	//--------------------------------------------------------------------------
	@Override
	public String toString() {
		// 
		StringBuilder builder = new StringBuilder();
		builder.append("id:" + id);
		builder.append(", name:" + name);
		builder.append(", description:" + description);
		builder.append(", admin:" + admin.getName());
		builder.append(", member count:" + roster.getMembers().size());
		
		return builder.toString();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id; 
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpenDate() {
		return openDate;
	}
	
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
	public void setRoster(ClubRoster roster) {
		this.roster = roster; 
	}
	
	public ClubRoster getRoster() {
		return roster; 
	}
	
	public ClubAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(ClubAdmin admin) {
		this.admin = admin;
	}
}