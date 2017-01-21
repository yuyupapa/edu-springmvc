/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.entity;

import namoo.club.util.enumtype.Gender;
import namoo.club.util.identity.Identifiable;

public class Towner implements Identifiable {
	//
	private String id; 
	private String name;
	private String email;
	private String password;
	private Gender gender;

	//--------------------------------------------------------------------------
	public Towner(){
		//
	}
	
	public Towner(String id) {
		//
		this.id = id;
	}

	public Towner(String name, String email, String password){
		//
		this(); 
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	//--------------------------------------------------------------------------
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}