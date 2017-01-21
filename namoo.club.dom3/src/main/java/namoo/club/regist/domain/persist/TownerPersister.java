/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.persist;

import namoo.club.regist.domain.entity.Towner;

public interface TownerPersister {
	//
	public String create(Towner towner);
	
	public Towner retrieve(String townerId);
	public Towner retrieveByEmail(String email); 
	public void update(Towner towner);
	public void delete(Towner towner);
	
	public boolean isExistEmail(String email);
}
