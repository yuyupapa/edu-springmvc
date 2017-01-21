/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.util.store;

import namoo.club.util.identity.Identifiable;

public interface EntityManager {
	// 
	public void store(Identifiable entity); 
	public <T> T find(Class<T> entityClass, String primaryKey); 
	public <T> T find(Class<T> entityClass, String fieldName, String fieldValue); 
	public void remove(Identifiable entity); 
	public <T> T merge(T entity);  
}